package com.mv.hibernate.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Product Entity - represents banking products
 * Used in Many-to-Many relationship with Customer
 */
@Entity
@Table(name = "product")
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    @Column(name = "type", length = 50)
    private String type;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "base_charges", precision = 10, scale = 2)
    private BigDecimal baseCharges;
    
    // Constructors
    public Product() {
    }
    
    public Product(String name, String type, String description, BigDecimal baseCharges) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.baseCharges = baseCharges;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public BigDecimal getBaseCharges() {
        return baseCharges;
    }
    
    public void setBaseCharges(BigDecimal baseCharges) {
        this.baseCharges = baseCharges;
    }
    
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", baseCharges=" + baseCharges +
                '}';
    }
}
