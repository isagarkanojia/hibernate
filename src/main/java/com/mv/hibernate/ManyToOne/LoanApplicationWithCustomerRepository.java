package com.mv.hibernate.ManyToOne;

import com.mv.hibernate.model.LoanApplicationWithCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for LoanApplicationWithCustomer entity
 * Spring Data JPA automatically provides CRUD operations
 */
@Repository
public interface LoanApplicationWithCustomerRepository extends JpaRepository<LoanApplicationWithCustomer, Long> {
    
    /**
     * Find all loan applications by customer ID
     * Spring Data JPA automatically generates the query from method name
     */
    List<LoanApplicationWithCustomer> findByCustomerId(Long customerId);
    
    /**
     * Find all loan applications by product type
     */
    List<LoanApplicationWithCustomer> findByProductType(String productType);
    
    /**
     * Find all loan applications by status
     */
    List<LoanApplicationWithCustomer> findByStatus(String status);
}
