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
    
    /**
     * ❌ INEFFICIENT: JPQL with correlated subqueries
     * 
     * Problem: Hibernate generates correlated subqueries that execute for EACH customer row!
     * For 10 customers, this generates many additional subquery executions.
     */
    @Query("""
        SELECT 
            c.id,
            c.name,
            c.pan,
            (SELECT COUNT(la) FROM LoanApplication la WHERE la.customerId = c.id) as loanCount,
            (SELECT COALESCE(SUM(la.requestedAmount), 0) FROM LoanApplication la WHERE la.customerId = c.id) as totalAmount
        FROM Customer c
        WHERE c.id IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        ORDER BY totalAmount DESC
        """)
    List<Object[]> findCustomerStatsInefficient();

    /**
     * ✅ EFFICIENT: Native SQL with JOIN and GROUP BY
     * 
     * Solution: Single query with JOIN - executes once!
     * This is the proper way to do aggregations in SQL.
     */
    @Query(value = """
        SELECT 
            c.id,
            c.name,
            c.pan,
            COUNT(la.id) as loan_count,
            COALESCE(SUM(la.requested_amount), 0) as total_amount
        FROM customer c
        LEFT JOIN loan_application la ON c.id = la.customer_id
        WHERE c.id IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        GROUP BY c.id, c.name, c.pan
        ORDER BY total_amount DESC
        """, nativeQuery = true)
    List<Object[]> findCustomerStatsEfficient();
}