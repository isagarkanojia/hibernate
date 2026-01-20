package com.mv.hibernate.SecondLevelCache;

import com.mv.hibernate.model.Customer;
import com.mv.hibernate.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Demonstration of Hibernate Second Level Cache with Caffeine.
 *
 * Key Differences from First Level Cache:
 * - First Level Cache: Lives within a single Session/Transaction
 * - Second Level Cache: Shared across multiple Sessions/Transactions
 * - Caffeine provides high-performance caching with detailed statistics
 *
 * This example shows:
 * 1. Multiple transactions accessing the same entity (cache is shared)
 * 2. Cache persists across different method calls
 * 3. Cache is evicted when entity is updated
 * 4. Cache statistics and monitoring
 */
@Service
public class SecondLevelCacheExample {

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Demonstrates that Second Level Cache works across different transactions.
     * Each method call creates a new transaction, but the cache is shared.
     */
    public void demonstrateSecondLevelCache() {
        System.out.println("\n=== Caffeine Second Level Cache Demo ===\n");

        System.out.println("1. First transaction - fetching customer with ID 1:");
        Customer customer1 = fetchCustomerInTransaction(1L);
        System.out.println("   Found: " + customer1.getName());

        showCacheStats();

        System.out.println("\n2. Second transaction (different method) - fetching same customer:");
        Customer customer2 = fetchCustomerInSeparateTransaction(1L);
        System.out.println("   Found: " + customer2.getName());
        System.out.println("   → This should NOT hit the database (served from Caffeine Second Level Cache)");

        showCacheStats();

        System.out.println("\n3. Third transaction - fetching same customer:");
        Customer customer3 = fetchCustomerInSeparateTransaction(1L);
        System.out.println("   Found: " + customer3.getName());
        System.out.println("   → This should also NOT hit the database (still cached)");

        showCacheStats();

        System.out.println("\n4. Updating customer (will invalidate cache):");
        updateCustomerInTransaction(1L, "Updated Name - Caffeine Cache");

        showCacheStats();

        System.out.println("\n5. Fetching after update - cache was invalidated:");
        Customer customer4 = fetchCustomerInSeparateTransaction(1L);
        System.out.println("   Found: " + customer4.getName());
        System.out.println("   → This WILL hit the database (cache was cleared on update)");

        showCacheStats();

        System.out.println("\n6. Fetching again - now cached again:");
        Customer customer5 = fetchCustomerInSeparateTransaction(1L);
        System.out.println("   Found: " + customer5.getName());
        System.out.println("   → This should NOT hit the database (served from cache)\n");

        showCacheStats();
    }

    /**
     * Demonstrates query cache (caching query results).
     * Query cache stores the results of queries, not just entities.
     */
    public void demonstrateQueryCache() {
        System.out.println("\n=== Query Cache Demo with Caffeine ===\n");

        System.out.println("1. First query - fetching customers with risk score > 700:");
        var customers1 = findCustomersByRiskScore(700);
        System.out.println("   Found " + customers1.size() + " customers");

        showCacheStats();

        System.out.println("\n2. Same query again - should use query cache:");
        var customers2 = findCustomersByRiskScore(700);
        System.out.println("   Found " + customers2.size() + " customers");
        System.out.println("   → This should NOT hit the database (served from Query Cache)");

        showCacheStats();

        System.out.println("\n3. Different query - fetching customers with risk score > 750:");
        var customers3 = findCustomersByRiskScore(750);
        System.out.println("   Found " + customers3.size() + " customers");
        System.out.println("   → This WILL hit the database (different query)\n");

        showCacheStats();
    }

    private void showCacheStats() {
        System.out.println("   💾 Caffeine Second Level Cache is active");
        System.out.println("      - Entity cache: com.mv.hibernate.model.Customer");
        System.out.println("      - Query cache: default-query-results-region");
        System.out.println("      - Update timestamps: default-update-timestamps-region");
        System.out.println("      - Monitor SQL logs above to see cache hits/misses");
    }

    @Transactional
    private Customer fetchCustomerInTransaction(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Transactional
    private Customer fetchCustomerInSeparateTransaction(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Transactional
    private void updateCustomerInTransaction(Long id, String newName) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer != null) {
            customer.setName(newName);
            customerRepository.save(customer);
            System.out.println("   ✓ Updated customer name to: " + newName);
        }
    }

    @Transactional(readOnly = true)
    private java.util.List<Customer> findCustomersByRiskScore(Integer riskScore) {
        // Note: For query cache to work, you need to use @QueryHints annotation
        // This is a simplified example - in practice, you'd add @QueryHints
        return customerRepository.findAll().stream()
                .filter(c -> c.getRiskScore() > riskScore)
                .toList();
    }
}