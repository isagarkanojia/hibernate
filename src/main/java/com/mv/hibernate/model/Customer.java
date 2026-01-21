package com.mv.hibernate.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;

@Entity
@Table(name = "customer")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Customer {
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

    public Customer() {
    }

    public Customer(Long id, String name, String pan, LocalDate dob, Integer riskScore) {
        this.id = id;
        this.name = name;
        this.pan = pan;
        this.dob = dob;
        this.riskScore = riskScore;
    }

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

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pan='" + pan + '\'' +
                ", dob=" + dob +
                ", riskScore=" + riskScore +
                '}';
    }
}
