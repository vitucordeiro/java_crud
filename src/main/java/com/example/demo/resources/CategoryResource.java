package com.example.demo.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.dto.CategoryDTO;
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
    public ResponseEntity<List<CategoryDTO>> findAll(){
       List<CategoryDTO> list = service.findAll();
       return ResponseEntity.ok().body(list);
    }

    // path variables 
    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id){
       CategoryDTO dto = service.findById(id);
       return ResponseEntity.ok().body(dto);
    }
    @PostMapping
    public ResponseEntity<CategoryDTO> insertCategory(@RequestBody CategoryDTO dto){
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }
}
 