package com.mv.hibernate.SecondLevelCache;

import com.mv.hibernate.model.Customer;
import com.mv.hibernate.repository.CustomerRepository;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Basic Second Level Cache Example
 * 
 * Demonstrates that second level cache is shared across different transactions.
 * Unlike first level cache (transaction-scoped), second level cache persists
 * across the entire application lifecycle.
 */
@Service
public class SecondLevelCacheExample {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    /**
     * Transaction 1: Fetches customer from database
     * This loads the entity and stores it in the second level cache
     */
    @Transactional(readOnly = true)
    public Customer transaction1_FetchFromDatabase(Long customerId) {
        System.out.println("\n[Transaction 1] Starting...");
        System.out.println("[Transaction 1] Fetching customer " + customerId + " - Will hit database");
        
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        System.out.println("[Transaction 1] Found: " + customer.getName());
        System.out.println("[Transaction 1] Customer loaded from DB and stored in L2 cache");
        
        return customer;
    }

    /**
     * Transaction 2: Fetches same customer
     * This retrieves from second level cache - NO database hit!
     */
    @Transactional(readOnly = true)
    public Customer transaction2_FetchFromCache(Long customerId) {
        System.out.println("\n[Transaction 2] Starting...");
        System.out.println("[Transaction 2] Fetching customer " + customerId + " - Should use L2 cache");
        
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        System.out.println("[Transaction 2] Found: " + customer.getName());
        System.out.println("[Transaction 2] Customer loaded from L2 cache (NO DB query!)");
        
        return customer;
    }

    /**
     * Transaction 3: Another fetch to verify cache is still working
     */
    @Transactional(readOnly = true)
    public Customer transaction3_FetchFromCache(Long customerId) {
        System.out.println("\n[Transaction 3] Starting...");
        System.out.println("[Transaction 3] Fetching customer " + customerId + " - Should use L2 cache");
        
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        System.out.println("[Transaction 3] Found: " + customer.getName());
        System.out.println("[Transaction 3] Customer loaded from L2 cache (NO DB query!)");
        
        return customer;
    }

    /**
     * Print cache statistics to see hits, misses, and puts
     */
    public void printCacheStatistics() {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        Statistics stats = sessionFactory.getStatistics();
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SECOND LEVEL CACHE STATISTICS");
        System.out.println("=".repeat(70));
        System.out.println("L2 Cache Hits:   " + stats.getSecondLevelCacheHitCount());
        System.out.println("L2 Cache Misses: " + stats.getSecondLevelCacheMissCount());
        System.out.println("L2 Cache Puts:   " + stats.getSecondLevelCachePutCount());
        System.out.println("=".repeat(70) + "\n");
    }

    /**
     * Clear cache statistics for a fresh start
     */
    public void clearStatistics() {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        sessionFactory.getStatistics().clear();
    }

    /**
     * Main demonstration method
     */
    public void demonstrate() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("=== SECOND LEVEL CACHE DEMONSTRATION ===");
        System.out.println("=".repeat(70));
        
        // Clear statistics for clean demo
        clearStatistics();
        
        // Transaction 1: Load from database
        Customer customer1 = transaction1_FetchFromDatabase(1L);
        
        // Transaction 2: Load from L2 cache (different transaction!)
        Customer customer2 = transaction2_FetchFromCache(1L);
        
        // Transaction 3: Load from L2 cache again
        Customer customer3 = transaction3_FetchFromCache(1L);
        
        // Show that different transactions return different object instances
        System.out.println("\n" + "-".repeat(70));
        System.out.println("COMPARING OBJECT INSTANCES:");
        System.out.println("-".repeat(70));
        System.out.println("customer1 == customer2: " + (customer1 == customer2));
        System.out.println("customer2 == customer3: " + (customer2 == customer3));
        System.out.println("\n✓ Different transactions = different object instances");
        System.out.println("✓ But data comes from L2 cache (shared across transactions)");
        System.out.println("=".repeat(70));
        
        // Print statistics
        printCacheStatistics();
        
        System.out.println("KEY TAKEAWAYS:");
        System.out.println("1. First transaction hits database (L2 cache miss + put)");
        System.out.println("2. Subsequent transactions hit L2 cache (NO database query)");
        System.out.println("3. Each transaction gets different object instance");
        System.out.println("4. L2 cache is shared across entire application");
        System.out.println("=".repeat(70) + "\n");
    }
}
