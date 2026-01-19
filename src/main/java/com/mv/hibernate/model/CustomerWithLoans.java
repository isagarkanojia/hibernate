package com.mv.hibernate.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * CustomerWithLoans Entity - demonstrates One-to-Many relationship
 * One Customer can have Many Loan Applications
 * This is a teaching example to show @OneToMany mapping
 */
@Entity
@Table(name = "customer")
public class CustomerWithLoans {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    @Column(name = "pan", nullable = false, length = 20, unique = true)
    private String pan;
    
    @Column(name = "dob", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dob;
    
    @Column(name = "risk_score", nullable = false)
    private Integer riskScore;
    
    /**
     * ONE-TO-MANY RELATIONSHIP:
     * - One Customer can have Many LoanApplications
     * - @OneToMany: Defines the one-to-many relationship
     * - mappedBy: Not used here because we're using @JoinColumn
     * - @JoinColumn: Specifies the foreign key column in the LoanApplication table
     * - FetchType.LAZY: Loads only when accessed (allows demonstration of N+1 problem)
     * - FetchType.EAGER: Would load immediately (prevents N+1 but uses memory)
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id") // This is the foreign key column in loan_application table
    private List<LoanApplication> loanApplications = new ArrayList<>();
    
    // Constructors
    public CustomerWithLoans() {
    }
    
    public CustomerWithLoans(String name, String pan, Date dob, Integer riskScore) {
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
    
    public List<LoanApplication> getLoanApplications() {
        return loanApplications;
    }
    
    public void setLoanApplications(List<LoanApplication> loanApplications) {
        this.loanApplications = loanApplications;
    }
    
    @Override
    public String toString() {
        return "CustomerWithLoans{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pan='" + pan + '\'' +
                ", dob=" + dob +
                ", riskScore=" + riskScore +
                '}';
    }
}
