package com.example.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.entities.Category;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.services.exceptions.DatabaseException;
import com.example.demo.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;


// Registra um componente como sendo injetável pelo Spring -> Habilita a DI
@Service
public class CategoryService {

    @Autowired // Injetando uma instância de um componente que é gerenciada pelo Spring
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAllPaged(Pageable pageable){
        Page<Category> rawPage = repository.findAll(pageable);
        return rawPage.map( x -> new CategoryDTO(x));
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
            throw new ResourceNotFoundException("Id not found:" + id);
        }
    }
    //Transactional não é utilizado, pois queremos retornar uma exception do BD
    public void delete(Long id) {
        try {
            if (!repository.existsById(id)){
                throw new ResourceNotFoundException("Id not found: " + id);  // Caso o id não exista, lança uma exceção personalizada
            }
            repository.deleteById(id);
        } 
        catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found" + id);
        }
        catch (DataIntegrityViolationException e) { 
            throw new DatabaseException("Cannot delete category. It is being referenced by other entities.");
        }
    }
}
