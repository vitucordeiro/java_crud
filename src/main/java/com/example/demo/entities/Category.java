package com.example.demo.entities;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//Tranforma a classe em um entidade -> associando a um DB
@Entity
//Dita o nome da tabela da entidade
@Table(name = "tb_category")
// Serializable -> utilizado para que o objeto java possa ser convertido em uma sequencia de Bytes
public class Category implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    //Criando um ID autoincrementável
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(columnDefinition= "TIMESTAMP WITHOUT TIME ZONE")
    private Instant created_At;

    @Column(columnDefinition= "TIMESTAMP WITHOUT TIME ZONE")
    private Instant updated_At;

    public Category(){}
    
    public Category(Long id, String name, Instant created_At ){
        this.id = id;
        this.name = name;
        this.created_At = created_At;
        this.updated_At = created_At;
    }


    public Instant getUpdated_At(){
        return this.updated_At;
    }
    public Instant getCreated_At(){
        return this.created_At;
    }
    
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Category)) {
            return false;
        }
        Category category = (Category) o;
        return Objects.equals(id, category.id) && Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
    

}
