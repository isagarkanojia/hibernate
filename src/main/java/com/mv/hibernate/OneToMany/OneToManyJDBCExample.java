package com.mv.hibernate.OneToMany;

import com.mv.hibernate.model.LoanApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ONE-TO-MANY JDBC EXAMPLE
 * 
 * This service demonstrates how to fetch a Customer with their LoanApplications
 * using JDBC PreparedStatement (Raw SQL)
 * 
 * Key Concepts:
 * 1. Manual SQL queries to fetch customer and related loan applications
 * 2. Two separate queries: one for customer, one for loan applications
 * 3. ResultSet processing to convert database rows to Java objects
 * 4. No automatic relationship management - we handle it manually
 */
@Service
public class OneToManyJDBCExample {
    
    private static final Logger logger = LoggerFactory.getLogger(OneToManyJDBCExample.class);
    
    @Autowired
    private DataSource dataSource;
    
    /**
     * Container class to hold Customer data with their Loan Applications
     */
    public static class CustomerWithLoanData {
        private Long id;
        private String name;
        private String pan;
        private Date dob;
        private Integer riskScore;
        private List<LoanApplication> loanApplications;
        
        public CustomerWithLoanData() {
            this.loanApplications = new ArrayList<>();
        }
        
        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getPan() { return pan; }
        public void setPan(String pan) { this.pan = pan; }
        
        public Date getDob() { return dob; }
        public void setDob(Date dob) { this.dob = dob; }
        
        public Integer getRiskScore() { return riskScore; }
        public void setRiskScore(Integer riskScore) { this.riskScore = riskScore; }
        
        public List<LoanApplication> getLoanApplications() { return loanApplications; }
        public void setLoanApplications(List<LoanApplication> loanApplications) { 
            this.loanApplications = loanApplications; 
        }
        
        @Override
        public String toString() {
            return "CustomerWithLoanData{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", pan='" + pan + '\'' +
                    ", dob=" + dob +
                    ", riskScore=" + riskScore +
                    ", numberOfLoanApplications=" + (loanApplications != null ? loanApplications.size() : 0) +
                    '}';
        }
    }
    
    /**
     * EXAMPLE 1: Get Customer with All Loan Applications using JDBC
     * 
     * How it works:
     * 1. Execute SQL query to fetch customer by ID
     * 2. Execute second SQL query to fetch all loan applications for that customer
     * 3. Manually combine the data into a CustomerWithLoanData object
     * 
     * This demonstrates the manual work required when not using JPA/Hibernate
     */
    public void getCustomerWithLoanApplications(Long customerId) {
        logger.info("\n========================================");
        logger.info("JDBC Example: Fetching Customer with Loan Applications");
        logger.info("========================================");
        
        // Step 1: Fetch customer data
        CustomerWithLoanData customer = fetchCustomerById(customerId);
        
        if (customer == null) {
            logger.warn("Customer with ID {} not found", customerId);
            return;
        }
        
        logger.info("Customer Found: {}", customer);
        
        // Step 2: Fetch loan applications for this customer
        List<LoanApplication> loanApplications = fetchLoanApplicationsByCustomerId(customerId);
        customer.setLoanApplications(loanApplications);
        
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
     * EXAMPLE 2: Get Customer by PAN with Loan Applications using JDBC
     */
    public void getCustomerByPanWithLoanApplications(String pan) {
        logger.info("\n========================================");
        logger.info("JDBC Example: Fetching Customer by PAN with Loan Applications");
        logger.info("========================================");
        
        CustomerWithLoanData customer = fetchCustomerByPan(pan);
        
        if (customer == null) {
            logger.warn("Customer with PAN {} not found", pan);
            return;
        }
        
        logger.info("Customer Found: {}", customer);
        
        List<LoanApplication> loanApplications = fetchLoanApplicationsByCustomerId(customer.getId());
        customer.setLoanApplications(loanApplications);
        
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
     * Helper Method: Fetch customer by ID using PreparedStatement
     */
    private CustomerWithLoanData fetchCustomerById(Long customerId) {
        String sql = "SELECT id, name, pan, dob, risk_score FROM customer WHERE id = ?";
        
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            preparedStatement.setLong(1, customerId);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractCustomerFromResultSet(resultSet);
                }
            }
            
        } catch (SQLException e) {
            logger.error("Error fetching customer with ID: {}", customerId, e);
            throw new RuntimeException("Error fetching customer", e);
        }
        
        return null;
    }
    
    /**
     * Helper Method: Fetch customer by PAN using PreparedStatement
     */
    private CustomerWithLoanData fetchCustomerByPan(String pan) {
        String sql = "SELECT id, name, pan, dob, risk_score FROM customer WHERE pan = ?";
        
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            preparedStatement.setString(1, pan);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractCustomerFromResultSet(resultSet);
                }
            }
            
        } catch (SQLException e) {
            logger.error("Error fetching customer with PAN: {}", pan, e);
            throw new RuntimeException("Error fetching customer", e);
        }
        
        return null;
    }
    
    /**
     * Helper Method: Fetch all loan applications for a customer using PreparedStatement
     * This demonstrates the "Many" side of the One-to-Many relationship
     */
    private List<LoanApplication> fetchLoanApplicationsByCustomerId(Long customerId) {
        List<LoanApplication> loanApplications = new ArrayList<>();
        String sql = "SELECT id, customer_id, product_type, requested_amount, tenure_months, status, created_at " +
                     "FROM loan_application WHERE customer_id = ? ORDER BY created_at DESC";
        
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            preparedStatement.setLong(1, customerId);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    LoanApplication loanApp = extractLoanApplicationFromResultSet(resultSet);
                    loanApplications.add(loanApp);
                }
            }
            
        } catch (SQLException e) {
            logger.error("Error fetching loan applications for customer ID: {}", customerId, e);
            throw new RuntimeException("Error fetching loan applications", e);
        }
        
        return loanApplications;
    }
    
    /**
     * Helper Method: Extract Customer from ResultSet
     */
    private CustomerWithLoanData extractCustomerFromResultSet(ResultSet resultSet) throws SQLException {
        CustomerWithLoanData customer = new CustomerWithLoanData();
        customer.setId(resultSet.getLong("id"));
        customer.setName(resultSet.getString("name"));
        customer.setPan(resultSet.getString("pan"));
        customer.setDob(resultSet.getDate("dob"));
        customer.setRiskScore(resultSet.getInt("risk_score"));
        return customer;
    }
    
    /**
     * Helper Method: Extract LoanApplication from ResultSet
     */
    private LoanApplication extractLoanApplicationFromResultSet(ResultSet resultSet) throws SQLException {
        LoanApplication loanApp = new LoanApplication();
        loanApp.setId(resultSet.getLong("id"));
        loanApp.setCustomerId(resultSet.getLong("customer_id"));
        loanApp.setProductType(resultSet.getString("product_type"));
        loanApp.setRequestedAmount(resultSet.getBigDecimal("requested_amount"));
        loanApp.setTenureMonths(resultSet.getInt("tenure_months"));
        loanApp.setStatus(resultSet.getString("status"));
        loanApp.setCreatedAt(resultSet.getTimestamp("created_at"));
        return loanApp;
    }
    
    /**
     * DEMO METHOD: Demonstrates JDBC One-to-Many data fetching
     * Call this method to see the example in action
     */
    public void demonstrateOneToManyMapping() {
        logger.info("\n╔═══════════════════════════════════════════════════════╗");
        logger.info("║   ONE-TO-MANY MAPPING DEMONSTRATION (JDBC)          ║");
        logger.info("╚═══════════════════════════════════════════════════════╝");
        
        // Example 1: Get customer ID 1 (Rohit Sharma) with loan applications
        getCustomerWithLoanApplications(1L);
        
        // Example 2: Get customer by PAN with loan applications
        getCustomerByPanWithLoanApplications("ABCDE1234F");
        
        // Example 3: Get customer ID 3 (Vikram Singh) with loan applications
        getCustomerWithLoanApplications(3L);
        
        logger.info("✅ JDBC One-to-Many demonstration completed!");
    }
}
