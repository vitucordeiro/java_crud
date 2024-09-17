package com.example.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Category;

//Aqui implementamos a camada de acesso a dados -> utilizando DB
// Extendemos a interface JpaRepository<entitie, ID> -> Passando a entidade e o tipo do ID da entidade
@Repository //Configura como um componente Repository que pode ser acessado pela camada de serviços
public interface CategoryRepository extends JpaRepository<Category, Long>{

}
