package com.mv.hibernate.OneToMany;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ONE-TO-MANY RELATIONSHIP DEMONSTRATION
 * 
 * This class demonstrates how to fetch a Customer with their Loan Applications
 * using two different approaches:
 * 
 * 1. JPA/Hibernate Approach (@OneToMany annotation)
 * 2. JDBC PreparedStatement Approach (Manual SQL)
 * 
 * Key Learning Points:
 * - JPA automatically manages relationships and lazy loading
 * - JDBC requires manual queries and data mapping
 * - JPA provides cleaner, more maintainable code
 * - JDBC gives more control but requires more work
 * 
 * Usage:
 * Call the runDemo() method to see both approaches in action
 */
@Service
public class OneToManyDemo {
    
    private static final Logger logger = LoggerFactory.getLogger(OneToManyDemo.class);
    
    @Autowired
    private OneToManyJPAExample jpaExample;
    
    @Autowired
    private OneToManyJDBCExample jdbcExample;
    
    /**
     * Run the complete demonstration showing both JPA and JDBC approaches
     */
    public void runDemo() {
        logger.info("\n");
        logger.info("╔══════════════════════════════════════════════════════════════════╗");
        logger.info("║                                                                  ║");
        logger.info("║        ONE-TO-MANY RELATIONSHIP DEMONSTRATION                    ║");
        logger.info("║        Customer -> Loan Applications                            ║");
        logger.info("║                                                                  ║");
        logger.info("╚══════════════════════════════════════════════════════════════════╝");
        
        // Demonstrate JPA approach
        logger.info("\n📚 Part 1: JPA/Hibernate Approach");
        logger.info("   - Uses @OneToMany annotation");
        logger.info("   - Automatic relationship management");
        logger.info("   - Lazy loading support\n");
        jpaExample.demonstrateOneToManyMapping();
        
        // Wait a moment for better readability
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Demonstrate JDBC approach
        logger.info("\n📚 Part 2: JDBC PreparedStatement Approach");
        logger.info("   - Manual SQL queries");
        logger.info("   - Explicit data mapping");
        logger.info("   - More control, more code\n");
        jdbcExample.demonstrateOneToManyMapping();
        
        // Summary
        logger.info("\n");
        logger.info("╔══════════════════════════════════════════════════════════════════╗");
        logger.info("║                          SUMMARY                                 ║");
        logger.info("╠══════════════════════════════════════════════════════════════════╣");
        logger.info("║  JPA Approach:                                                   ║");
        logger.info("║    ✓ Less code, cleaner syntax                                   ║");
        logger.info("║    ✓ Automatic relationship management                          ║");
        logger.info("║    ✓ Lazy loading support                                       ║");
        logger.info("║    ✓ Database independent (to some extent)                      ║");
        logger.info("║                                                                  ║");
        logger.info("║  JDBC Approach:                                                  ║");
        logger.info("║    ✓ More control over queries                                  ║");
        logger.info("║    ✓ Better performance for complex queries                     ║");
        logger.info("║    ✓ No ORM overhead                                            ║");
        logger.info("║    ✗ More boilerplate code                                      ║");
        logger.info("║    ✗ Manual relationship management                             ║");
        logger.info("╚══════════════════════════════════════════════════════════════════╝");
        logger.info("\n");
    }
    
    /**
     * Run only JPA example
     */
    public void runJPAExample() {
        logger.info("\n=== Running JPA Example Only ===\n");
        jpaExample.demonstrateOneToManyMapping();
    }
    
    /**
     * Run only JDBC example
     */
    public void runJDBCExample() {
        logger.info("\n=== Running JDBC Example Only ===\n");
        jdbcExample.demonstrateOneToManyMapping();
    }
}
