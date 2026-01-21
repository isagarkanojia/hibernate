package com.mv.hibernate.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * CustomerWithProducts Entity - demonstrates Many-to-Many relationship
 * Many Customers can have Many Products
 * This is a teaching example to show @ManyToMany mapping
 */
@Entity
@Table(name = "customer")
public class CustomerWithProducts {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    @Column(name = "pan", nullable = false, length = 20, unique = true)
    private String pan;
    
    @Column(name = "dob", nullable = false)
    private LocalDate dob;
    
    @Column(name = "risk_score", nullable = false)
    private Integer riskScore;
    
    /**
     * MANY-TO-MANY RELATIONSHIP:
     * - Many Customers can have Many Products
     * - Many Products can belong to Many Customers
     * - @ManyToMany: Defines the many-to-many relationship
     * - @JoinTable: Specifies the junction table that connects both entities
     * - joinColumns: Foreign key for THIS entity (customer_id)
     * - inverseJoinColumns: Foreign key for the OTHER entity (product_id)
     * - FetchType.EAGER: Loads products immediately with customer
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "customer_product",
        joinColumns = @JoinColumn(name = "customer_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products = new ArrayList<>();
    
    // Constructors
    public CustomerWithProducts() {
    }
    
    public CustomerWithProducts(String name, String pan, LocalDate dob, Integer riskScore) {
        this.name = name;
        this.pan = pan;
        this.dob = dob;
        this.riskScore = riskScore;
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
    
    public String getPan() {
        return pan;
    }
    
    public void setPan(String pan) {
        this.pan = pan;
    }
    
    public LocalDate getDob() {
        return dob;
    }
    
    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
    
    public Integer getRiskScore() {
        return riskScore;
    }
    
    public void setRiskScore(Integer riskScore) {
        this.riskScore = riskScore;
    }
    
    public List<Product> getProducts() {
        return products;
    }
    
    public void setProducts(List<Product> products) {
        this.products = products;
    }
    
    @Override
    public String toString() {
        return "CustomerWithProducts{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pan='" + pan + '\'' +
                ", riskScore=" + riskScore +
                ", numberOfProducts=" + (products != null ? products.size() : 0) +
                '}';
    }
}
