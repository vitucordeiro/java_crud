package com.example.demo.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.dto.ProductDTO;
import com.example.demo.services.ProductService;


// Annotation -> configura uma classe em um recurso Rest 
@RestController
// Configura o caminho da rota
@RequestMapping(value = "/v1/products")
// Implementa o controlador REST
public class ProductResource { //Se o recurso tem relação com entidade ->entitieName+Resource

    @Autowired //injeta a dependencia
    private ProductService service; //instância a dependencia

    // Encapsula uma resposta HTTP -> Generic
    @GetMapping()
    public ResponseEntity<Page<ProductDTO>> findAll(
        /*
         * 
         @RequestParam(value="page", defaultValue="0") Integer page,
         @RequestParam(value="linesPerPage", defaultValue="12") Integer linesPerPage,
         @RequestParam(value="direction", defaultValue="ASC") String direction,
         @RequestParam(value="orderBy", defaultValue="name") String orderBy
         */
        Pageable pageable
    ){  
        //Retornará uma Page -> Paginação
        // PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        
        //Refatorando -> Spring já instância como default os valores de size, sort e size
        Page<ProductDTO> rawPage = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(rawPage);
    }

    //Notas: Quando é obrigatório ser passado um argumento, usa-se @PathVariable. Quando não é, utiliza-se @RequestParams(value="", defaultValue= x ) Type var 
    //Exemplo abaixo, poderia passar o @RequestParams(value="id", defaultValue=1) Integer id 

    // path variables 
    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id){
       ProductDTO dto = service.findById(id);
       return ResponseEntity.ok().body(dto);
    }
    @PostMapping
    public ResponseEntity<ProductDTO> insertProduct(@RequestBody ProductDTO dto){
        dto = service.insert(dto);
        // TODO: URI is to ? 
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }
    
    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> insert(@PathVariable Long id, @RequestBody ProductDTO dto ){
        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }
    
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build(); //Return 204 e o corpo retorna vázio
    }
}
 