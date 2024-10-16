package com.example.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.ProductDTO;
import com.example.demo.entities.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.services.exceptions.DatabaseException;
import com.example.demo.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;


// Registra um componente como sendo injetável pelo Spring -> Habilita a DI
@Service
public class ProductService {

    @Autowired // Injetando uma instância de um componente que é gerenciada pelo Spring
    private ProductRepository repository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(PageRequest pageRequest){
        Page<Product> rawPage = repository.findAll(pageRequest);
        return rawPage.map( x -> new ProductDTO(x));
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){
        //Optional serve para trabalhar com valores que podem ou não serem nulos
        Optional<Product> obj = repository.findById(id);

        // Com o método get(), o Optional retorna a entidade
        //Product entity = obj.get();
        
        // O método orElseThrow conseguimos retornar outro valor se for nulo
        // Utilizei um Exception personalizado que criei como subpacote da camada services
        Product entity = obj.orElseThrow( () -> new ResourceNotFoundException("Entity not found"));
        return new ProductDTO(entity, entity.getCategories());
    }
    @Transactional
    public ProductDTO insert(ProductDTO dto){
        Product entity = new Product();
        //entity.setName(dto.getName());
        entity = repository.save(entity);
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO update(Long id,ProductDTO dto){
        try{
            Product entity = repository.getReferenceById(id);
            //entity.setName(dto.getName());
            entity = repository.save(entity);
            return new ProductDTO(entity);
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
        
        catch (DataIntegrityViolationException e) { 
            throw new DatabaseException("Integrity violation");
        }
    }
}
