package com.mv.hibernate.repository;

import com.mv.hibernate.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Spring Data JPA automatically generates this query based on method name
     * Finds customers with risk score greater than the specified value
     */
    List<Customer> findByRiskScoreGreaterThan(Integer riskScore);

    /**
     * Custom query using @Query annotation
     * Alternative way to define custom queries
     */
    @Query("SELECT c FROM Customer c WHERE c.riskScore > :riskScore")
    List<Customer> findHighRiskCustomers(@Param("riskScore") Integer riskScore);

    /**
     * Find customer by PAN (unique identifier)
     */
    Customer findByPan(String pan);

    /**
     * Count customers with risk score in a range
     */
    long countByRiskScoreBetween(Integer minRisk, Integer maxRisk);
}