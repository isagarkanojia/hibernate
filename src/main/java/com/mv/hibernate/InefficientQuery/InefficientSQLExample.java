package com.mv.hibernate.InefficientQuery;

import com.mv.hibernate.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Demonstrates inefficient SQL generation by Hibernate/Spring Data JPA
 * vs efficient native SQL for complex queries with aggregations.
 * 
 * Problem: Hibernate generates correlated subqueries for aggregations
 * Solution: Use native SQL with proper JOINs and GROUP BY
 */
@Service
public class InefficientSQLExample {

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Main demonstration method showing both approaches
     */
    public void demonstrateInefficientSQL() {
        System.out.println("\n" + "=".repeat(85));
        System.out.println("INEFFICIENT SQL GENERATION DEMONSTRATION");
        System.out.println("Scenario: Get customer loan statistics (count and total amount)");
        System.out.println("=".repeat(85));
        
        // ❌ Show inefficient approach
        demonstrateInefficient();
        
        System.out.println("\n" + "▼".repeat(42) + "\n");
        
        // ✅ Show efficient approach
        demonstrateEfficient();
        
        System.out.println("\n" + "=".repeat(85));
        System.out.println("KEY TAKEAWAYS:");
        System.out.println("❌ Inefficient: Multiple correlated subqueries (executed N times)");
        System.out.println("✅ Efficient: Single query with JOIN and GROUP BY (executed once)");
        System.out.println("💡 For complex queries with aggregations, always use NATIVE SQL!");
        System.out.println("=".repeat(85) + "\n");
    }

    /**
     * ❌ Demonstrates inefficient JPQL query with correlated subqueries
     */
    @Transactional(readOnly = true)
    public void demonstrateInefficient() {
        System.out.println("\n❌ INEFFICIENT APPROACH: JPQL with Correlated Subqueries");
        System.out.println("-".repeat(85));
        System.out.println("⚠️  Watch the SQL logs - you'll see correlated subqueries!");
        System.out.println("    Each subquery executes for EVERY customer row.\n");
        
        long startTime = System.currentTimeMillis();
        
        // Execute inefficient query
        List<Object[]> results = customerRepository.findCustomerStatsInefficient();
        
        long executionTime = System.currentTimeMillis() - startTime;
        
        // Print results
        printResults(results);
        
        System.out.println("\n⏱️  Execution Time: " + executionTime + " ms");
        System.out.println("📊 SQL Pattern: Main SELECT with 2 correlated subqueries");
        System.out.println("    Problem: Subqueries execute for each of " + results.size() + " customers");
        System.out.println("    Hidden cost: Multiple subquery executions!");
    }

    /**
     * ✅ Demonstrates efficient native SQL with proper JOIN and GROUP BY
     */
    @Transactional(readOnly = true)
    public void demonstrateEfficient() {
        System.out.println("\n✅ EFFICIENT APPROACH: Native SQL with JOIN + GROUP BY");
        System.out.println("-".repeat(85));
        System.out.println("✓ Watch the SQL logs - only ONE query with JOIN!");
        System.out.println("   All data fetched in a single, optimized query.\n");
        
        long startTime = System.currentTimeMillis();
        
        // Execute efficient query
        List<Object[]> results = customerRepository.findCustomerStatsEfficient();
        
        long executionTime = System.currentTimeMillis() - startTime;
        
        // Print results
        printResults(results);
        
        System.out.println("\n⏱️  Execution Time: " + executionTime + " ms");
        System.out.println("📊 SQL Pattern: Single SELECT with JOIN and GROUP BY");
        System.out.println("    Benefit: One query, regardless of customer count!");
    }

    /**
     * Helper method to print query results in a formatted table
     */
    private void printResults(List<Object[]> results) {
        System.out.printf("%-5s %-25s %-20s %12s %20s%n", 
            "ID", "Name", "PAN", "Loan Count", "Total Requested");
        System.out.println("-".repeat(85));
        
        for (Object[] row : results) {
            // Handle different number types from JPQL vs Native SQL
            Long id = convertToLong(row[0]);
            String name = (String) row[1];
            String pan = (String) row[2];
            Long loanCount = convertToLong(row[3]);
            BigDecimal totalAmount = convertToBigDecimal(row[4]);
            
            System.out.printf("%-5d %-25s %-20s %12d %20.2f%n",
                id, 
                name, 
                pan, 
                loanCount, 
                totalAmount
            );
        }
        
        System.out.println("-".repeat(85));
        System.out.println("Total customers: " + results.size());
    }

    /**
     * Helper to convert various number types to Long
     */
    private Long convertToLong(Object value) {
        if (value instanceof Long) {
            return (Long) value;
        } else if (value instanceof Integer) {
            return ((Integer) value).longValue();
        } else if (value instanceof BigDecimal) {
            return ((BigDecimal) value).longValue();
        }
        return 0L;
    }

    /**
     * Helper to convert various number types to BigDecimal
     */
    private BigDecimal convertToBigDecimal(Object value) {
        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        } else if (value instanceof Double) {
            return BigDecimal.valueOf((Double) value);
        } else if (value instanceof Long) {
            return BigDecimal.valueOf((Long) value);
        } else if (value instanceof Integer) {
            return BigDecimal.valueOf((Integer) value);
        }
        return BigDecimal.ZERO;
    }
}
