package com.mv.hibernate.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * LoanApplicationWithCustomer Entity - demonstrates Many-to-One relationship
 * Many Loan Applications belong to One Customer
 * This is a teaching example to show @ManyToOne mapping
 */
@Entity
@Table(name = "loan_application")
public class LoanApplicationWithCustomer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "customer_id", nullable = false, insertable = false, updatable = false)
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
    private LocalDateTime createdAt;
    
    /**
     * MANY-TO-ONE RELATIONSHIP:
     * - Many LoanApplications belong to One Customer
     * - @ManyToOne: Defines the many-to-one relationship
     * - @JoinColumn: Specifies the foreign key column that joins to the customer table
     * - FetchType.EAGER: Loads customer immediately with loan application
     * - This is the "owning" side of the relationship (has the foreign key)
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;
    
    // Constructors
    public LoanApplicationWithCustomer() {
    }
    
    public LoanApplicationWithCustomer(Long customerId, String productType, BigDecimal requestedAmount, 
                                      Integer tenureMonths, String status, LocalDateTime createdAt) {
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
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    @Override
    public String toString() {
        return "LoanApplicationWithCustomer{" +
                "id=" + id +
                ", productType='" + productType + '\'' +
                ", requestedAmount=" + requestedAmount +
                ", tenureMonths=" + tenureMonths +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", customerName='" + (customer != null ? customer.getName() : "N/A") + '\'' +
                '}';
    }
}
