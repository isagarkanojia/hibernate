package com.mv.hibernate.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * LoanApplication Entity - represents a loan application submitted by a customer
 * This demonstrates the "Many" side of the One-to-Many relationship with Customer
 */
@Entity
@Table(name = "loan_application")
public class LoanApplication {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "customer_id", nullable = false)
    private Long customerId;
    
    @Column(name = "product_type", length = 50)
    private String productType;
    
    @Column(name = "requested_amount", precision = 15, scale = 2)
    private BigDecimal requestedAmount;
    
    @Column(name = "tenure_months")
    private Integer tenureMonths;
    
    @Column(name = "status", length = 30)
    private String status;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    // Constructors
    public LoanApplication() {
    }
    
    public LoanApplication(Long customerId, String productType, BigDecimal requestedAmount, 
                          Integer tenureMonths, String status, Date createdAt) {
        this.customerId = customerId;
        this.productType = productType;
        this.requestedAmount = requestedAmount;
        this.tenureMonths = tenureMonths;
        this.status = status;
        this.createdAt = createdAt;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    
    public String getProductType() {
        return productType;
    }
    
    public void setProductType(String productType) {
        this.productType = productType;
    }
    
    public BigDecimal getRequestedAmount() {
        return requestedAmount;
    }
    
    public void setRequestedAmount(BigDecimal requestedAmount) {
        this.requestedAmount = requestedAmount;
    }
    
    public Integer getTenureMonths() {
        return tenureMonths;
    }
    
    public void setTenureMonths(Integer tenureMonths) {
        this.tenureMonths = tenureMonths;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Date getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
    @Override
    public String toString() {
        return "LoanApplication{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", productType='" + productType + '\'' +
                ", requestedAmount=" + requestedAmount +
                ", tenureMonths=" + tenureMonths +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
