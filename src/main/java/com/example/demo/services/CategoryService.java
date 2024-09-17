package com.example.demo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.entities.Category;
import com.example.demo.repository.CategoryRepository;

// Registra um componente como sendo injetável pelo Spring -> Habilita a DI
@Service
public class CategoryService {

    @Autowired // Injetando uma instância de um componente que é gerenciada pelo Spring
    private CategoryRepository repository;

    public List<CategoryDTO> findAll(){
        List<Category> list = repository.findAll();
        return list.stream().map( x -> new CategoryDTO(x)).collect(Collectors.toList());
    }

}
