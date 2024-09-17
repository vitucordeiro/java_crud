package com.example.demo.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Category;
import com.example.demo.services.CategoryService;

// Annotation -> configura uma classe em um recurso Rest 
@RestController
// Configura o caminho da rota
@RequestMapping(value = "/categories")
// Implementa o controlador REST
public class CategoryResource { //Se o recurso tem relação com entidade ->entitieName+Resource

    @Autowired //injeta a dependencia
    private CategoryService service; //instância a dependencia

    // Encapsula uma resposta HTTP -> Generic
    @GetMapping()
    public ResponseEntity<List<Category>> findAll(){
       List<Category> list = service.findAll();
       return ResponseEntity.ok().body(list);
    }

}
