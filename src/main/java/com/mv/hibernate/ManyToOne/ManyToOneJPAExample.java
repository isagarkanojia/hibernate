package com.mv.hibernate.ManyToOne;

import com.mv.hibernate.model.Customer;
import com.mv.hibernate.model.LoanApplicationWithCustomer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * MANY-TO-ONE JPA EXAMPLE
 * 
 * This service demonstrates how to fetch LoanApplications with their Customer
 * using JPA (Java Persistence API) with Hibernate
 * 
 * Key Concepts:
 * 1. @ManyToOne relationship: Many Loan Applications belong to One Customer
 * 2. Eager Loading: Customer is loaded automatically with loan application
 * 3. This is the INVERSE of the One-to-Many relationship
 */
@Service
public class ManyToOneJPAExample {
    
    private static final Logger logger = LoggerFactory.getLogger(ManyToOneJPAExample.class);
    
    @Autowired
    private LoanApplicationWithCustomerRepository loanApplicationRepository;
    
    /**
     * EXAMPLE 1: Get Loan Application by ID with Customer Details
     * 
     * How it works:
     * 1. Fetch loan application by ID using JPA repository
     * 2. Customer is automatically loaded due to @ManyToOne with EAGER fetch
     * 3. Hibernate automatically joins to customer table
     */
    @Transactional(readOnly = true)
    public void getLoanApplicationWithCustomer(Long loanApplicationId) {
        logger.info("\n========================================");
        logger.info("JPA Example: Fetching Loan Application with Customer");
        logger.info("========================================");
        
        // Step 1: Find loan application by ID
        Optional<LoanApplicationWithCustomer> loanAppOpt = loanApplicationRepository.findById(loanApplicationId);
        
        if (loanAppOpt.isEmpty()) {
            logger.warn("Loan Application with ID {} not found", loanApplicationId);
            return;
        }
        
        LoanApplicationWithCustomer loanApp = loanAppOpt.get();
        logger.info("Loan Application Found: {}", loanApp);

        
        logger.info("\nLoan Application Details:");
        logger.info("  - Product Type: {}", loanApp.getProductType());
        logger.info("  - Requested Amount: ₹{}", loanApp.getRequestedAmount());
        logger.info("  - Tenure: {} months", loanApp.getTenureMonths());
        logger.info("  - Status: {}", loanApp.getStatus());


        // Step 2: Access customer (already loaded automatically)
        Customer customer = loanApp.getCustomer();

        if (customer != null) {
            logger.info("\nCustomer Details:");
            logger.info("  - ID: {}", customer.getId());
            logger.info("  - Name: {}", customer.getName());
            logger.info("  - PAN: {}", customer.getPan());
            logger.info("  - Risk Score: {}", customer.getRiskScore());
        }
        
        logger.info("========================================\n");
    }
    
    /**
     * EXAMPLE 2: Get All Loan Applications for a Specific Customer
     * Shows how to query by foreign key (customer_id)
     */
    @Transactional(readOnly = true)
    public void getLoanApplicationsByCustomerId(Long customerId) {
        logger.info("\n========================================");
        logger.info("JPA Example: Fetching All Loan Applications for Customer ID: {}", customerId);
        logger.info("========================================");
        
        List<LoanApplicationWithCustomer> loanApplications = loanApplicationRepository.findByCustomerId(customerId);
        
        if (loanApplications.isEmpty()) {
            logger.warn("No loan applications found for Customer ID {}", customerId);
            return;
        }
        
        logger.info("Found {} loan application(s) for Customer ID: {}", loanApplications.size(), customerId);
        
        // Get customer name from first application
        String customerName = loanApplications.get(0).getCustomer() != null ? 
                              loanApplications.get(0).getCustomer().getName() : "Unknown";
        
        logger.info("\nCustomer: {}", customerName);
        logger.info("Loan Applications:");
        logger.info("----------------------------------------");
        
        for (LoanApplicationWithCustomer loanApp : loanApplications) {
            logger.info("  - ID: {}, Type: {}, Amount: ₹{}, Status: {}", 
                    loanApp.getId(), 
                    loanApp.getProductType(), 
                    loanApp.getRequestedAmount(), 
                    loanApp.getStatus());
        }
        
        logger.info("========================================\n");
    }
    
    /**
     * EXAMPLE 3: Get All Loan Applications by Product Type
     * Shows querying by loan application attributes
     */
    @Transactional(readOnly = true)
    public void getLoanApplicationsByProductType(String productType) {
        logger.info("\n========================================");
        logger.info("JPA Example: Fetching All {} Loan Applications", productType);
        logger.info("========================================");
        
        List<LoanApplicationWithCustomer> loanApplications = loanApplicationRepository.findByProductType(productType);
        
        logger.info("Found {} {} loan application(s)", loanApplications.size(), productType);
        logger.info("----------------------------------------");
        
        for (LoanApplicationWithCustomer loanApp : loanApplications) {
            Customer customer = loanApp.getCustomer();
            String customerName = customer != null ? customer.getName() : "Unknown";
            
            logger.info("  - ID: {}, Customer: {}, Amount: ₹{}, Status: {}", 
                    loanApp.getId(), 
                    customerName, 
                    loanApp.getRequestedAmount(), 
                    loanApp.getStatus());
        }
        
        logger.info("========================================\n");
    }
    
    /**
     * DEMO METHOD: Demonstrates ManyToOne relationship
     * Call this method to see the example in action
     */
    public void demonstrateManyToOneMapping() {
        logger.info("\n╔═══════════════════════════════════════════════════════╗");
        logger.info("║   MANY-TO-ONE MAPPING DEMONSTRATION (JPA)           ║");
        logger.info("╚═══════════════════════════════════════════════════════╝");
        
        // Example 1: Get loan application ID 1 with customer details
        getLoanApplicationWithCustomer(1L);
        
        // Example 2: Get all loan applications for customer ID 1 (Rohit Sharma)
        getLoanApplicationsByCustomerId(1L);
        
        // Example 3: Get all HOME_LOAN applications
        getLoanApplicationsByProductType("HOME_LOAN");
        
        logger.info("✅ JPA Many-to-One demonstration completed!");
    }
}
