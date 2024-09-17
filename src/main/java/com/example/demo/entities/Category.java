package com.example.demo.entities;
import java.io.Serializable;
import java.util.Objects;

// Serializable -> utilizado para que o objeto java possa ser convertido em uma sequencia de Bytes
public class Category implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;

    public Category(){}
    
    public Category(Long id, String name){
        this.id = id;
        this.name = name;
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
