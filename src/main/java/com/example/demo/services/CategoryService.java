package com.example.demo.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.entities.Category;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;


// Registra um componente como sendo injetável pelo Spring -> Habilita a DI
@Service
public class CategoryService {

    @Autowired // Injetando uma instância de um componente que é gerenciada pelo Spring
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll(){
        List<Category> list = repository.findAll();
        return list.stream().map( x -> new CategoryDTO(x)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id){
        //Optional serve para trabalhar com valores que podem ou não serem nulos
        Optional<Category> obj = repository.findById(id);

        // Com o método get(), o Optional retorna a entidade
        //Category entity = obj.get();
        
        // O método orElseThrow conseguimos retornar outro valor se for nulo
        // Utilizei um Exception personalizado que criei como subpacote da camada services
        Category entity = obj.orElseThrow( () -> new ResourceNotFoundException("Entity not found"));
        return new CategoryDTO(entity);
    }
    @Transactional
    public CategoryDTO insert(CategoryDTO dto){
        Category entity = new Category();
        entity.setName(dto.getName());
        entity = repository.save(entity);
        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO update(Long id,CategoryDTO dto){
        try{
            Category entity = repository.getReferenceById(id);
            entity.setName(dto.getName());
            entity = repository.save(entity);
            return new CategoryDTO(entity);
        } 
        catch(EntityNotFoundException e){
            throw  new ResourceNotFoundException("Id not found:" + id);
        }
    }
}
