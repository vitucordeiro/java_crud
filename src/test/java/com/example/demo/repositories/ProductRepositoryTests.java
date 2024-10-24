package com.example.demo.repositories;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.example.demo.entities.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.services.exceptions.ResourceNotFoundException;

@DataJpaTest
public class ProductRepositoryTests {
  
  @Autowired
  private ProductRepository repository;
    
  private long existingId;
  private long nonExistingId;
  @BeforeEach
  void setUp() throws Exception {
    existingId = 1L;
    nonExistingId = 50000L;
  }

  @Test
  public void deleteShouldDeleteObjectWhenIdExists() {
    repository.deleteById(existingId);
    Optional<Product> result = repository.findById(existingId);
    Assertions.assertThat(result).isEmpty();
  }
  
  @Test
  public void deleteShouldThrowEmptyResultDataExceptionWhenIdDoesNotExist(){
    Assertions.assertThat(repository.existsById(nonExistingId)).isFalse();

    repository.deleteById(nonExistingId);
    Assertions.assertThat(repository.existsById(nonExistingId)).isFalse();
  }

}
