package com.mv.hibernate.model;

import java.util.Date;

public class Customer {
    private Long id;
    private String name;
    private String pan;
    private Date dob;
    private Integer riskScore;

    public Customer() {
    }

    public Customer(Long id, String name, String pan, Date dob, Integer riskScore) {
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

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
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
