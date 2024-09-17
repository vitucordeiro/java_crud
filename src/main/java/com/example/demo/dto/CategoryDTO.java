package com.example.demo.dto;
import java.io.Serializable;
import java.util.Objects;

import com.example.demo.entities.Category;

public class CategoryDTO implements Serializable{
    private static final long serialVersionUID = 1L; 
    private Long id;
    private String name;
    

    public CategoryDTO() {
    }

    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDTO (Category entitiy){
        this.id = entitiy.getId();
        this.name = entitiy.getName();
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

    public CategoryDTO id(Long id) {
        setId(id);
        return this;
    }

    public CategoryDTO name(String name) {
        setName(name);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof CategoryDTO)) {
            return false;
        }
        CategoryDTO categoryDTO = (CategoryDTO) o;
        return Objects.equals(id, categoryDTO.id) && Objects.equals(name, categoryDTO.name);
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
