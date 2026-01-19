package com.mv.hibernate.OneToMany;

import com.mv.hibernate.model.CustomerWithLoans;
import com.mv.hibernate.model.LoanApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * ONE-TO-MANY JPA EXAMPLE
 * 
 * This service demonstrates how to fetch a Customer with their LoanApplications
 * using JPA (Java Persistence API) with Hibernate
 * 
 * Key Concepts:
 * 1. @OneToMany relationship: One Customer can have Many Loan Applications
 * 2. Lazy Loading: LoanApplications are loaded only when accessed
 * 3. Transaction Management: @Transactional ensures database session is open
 */
@Service
public class OneToManyJPAExample {
    
    private static final Logger logger = LoggerFactory.getLogger(OneToManyJPAExample.class);
    
    @Autowired
    private CustomerWithLoansRepository customerRepository;
    
    /**
     * EXAMPLE 1: Get Customer with All Loan Applications
     * 
     * How it works:
     * 1. Fetch customer by ID using JPA repository
     * 2. Access loanApplications list - triggers lazy loading
     * 3. Hibernate automatically fetches related loan applications
     * 
     * @Transactional ensures the database session stays open when accessing lazy-loaded data
     */
    @Transactional(readOnly = true)
    public void getCustomerWithLoanApplications(Long customerId) {
        logger.info("\n========================================");
        logger.info("JPA Example: Fetching Customer with Loan Applications");
        logger.info("========================================");
        
        // Step 1: Find customer by ID
        Optional<CustomerWithLoans> customerOpt = customerRepository.findById(customerId);
        
        if (customerOpt.isEmpty()) {
            logger.warn("Customer with ID {} not found", customerId);
            return;
        }
        
        CustomerWithLoans customer = customerOpt.get();
        logger.info("Customer Found: {}", customer);
        
        // Step 2: Access loan applications (triggers lazy loading)
        List<LoanApplication> loanApplications = customer.getLoanApplications();
        logger.info("Number of Loan Applications: {}", loanApplications.size());
        
        // Step 3: Display all loan applications
        logger.info("\nLoan Applications for Customer: {}", customer.getName());
        logger.info("----------------------------------------");
        for (LoanApplication loanApp : loanApplications) {
            logger.info("  - Application ID: {}, Type: {}, Amount: {}, Status: {}", 
                    loanApp.getId(), 
                    loanApp.getProductType(), 
                    loanApp.getRequestedAmount(), 
                    loanApp.getStatus());
        }
        
        logger.info("========================================\n");
    }
    
    /**
     * EXAMPLE 2: Get Customer by PAN with Loan Applications
     */
    @Transactional(readOnly = true)
    public void getCustomerByPanWithLoanApplications(String pan) {
        logger.info("\n========================================");
        logger.info("JPA Example: Fetching Customer by PAN with Loan Applications");
        logger.info("========================================");
        
        CustomerWithLoans customer = customerRepository.findByPan(pan);
        
        if (customer == null) {
            logger.warn("Customer with PAN {} not found", pan);
            return;
        }
        
        logger.info("Customer Found: {}", customer);
        
        List<LoanApplication> loanApplications = customer.getLoanApplications();
        logger.info("Number of Loan Applications: {}", loanApplications.size());
        
        for (LoanApplication loanApp : loanApplications) {
            logger.info("  - Application: {} - {} - Amount: {}", 
                    loanApp.getProductType(), 
                    loanApp.getStatus(),
                    loanApp.getRequestedAmount());
        }
        
        logger.info("========================================\n");
    }
    
    /**
     * DEMO METHOD: Demonstrates JPA One-to-Many relationship
     * Call this method to see the example in action
     */
    public void demonstrateOneToManyMapping() {
        logger.info("\n╔═══════════════════════════════════════════════════════╗");
        logger.info("║   ONE-TO-MANY MAPPING DEMONSTRATION (JPA)           ║");
        logger.info("╚═══════════════════════════════════════════════════════╝");
        
        // Example 1: Get customer ID 1 (Rohit Sharma) with loan applications
        getCustomerWithLoanApplications(1L);
        
        // Example 2: Get customer by PAN with loan applications
        getCustomerByPanWithLoanApplications("ABCDE1234F");
        
        // Example 3: Get customer ID 3 (Vikram Singh) with loan applications
        getCustomerWithLoanApplications(3L);
        
        logger.info("✅ JPA One-to-Many demonstration completed!");
    }
}
