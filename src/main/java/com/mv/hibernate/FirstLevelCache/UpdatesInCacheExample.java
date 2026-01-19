package com.mv.hibernate.FirstLevelCache;

import com.mv.hibernate.model.Customer;
import com.mv.hibernate.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Demonstrates that cached entities show uncommitted changes immediately.
 */
@Service
public class UpdatesInCacheExample {

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Main demonstration: Updates are immediately visible in cache
     */
    @Transactional
    public void demonstrate() {
        System.out.println("\n=== Updates Are Immediately Visible in Cache ===\n");
        
        // Fetch customer
        Customer customer = customerRepository.findById(1L).orElseThrow();
        System.out.println("Original name: " + customer.getName());
        
        // Modify entity in memory (NOT saved yet!)
        customer.setName("MODIFIED NAME");
        System.out.println("Modified to: " + customer.getName());
        
        // Fetch again - will return the SAME modified object from cache
        Customer refetched = customerRepository.findById(1L).orElseThrow();
        System.out.println("Refetched name: " + refetched.getName());
        
        // Check if same object
        System.out.println("Same object? " + (customer == refetched));
        System.out.println("→ Cache shows uncommitted changes!\n");
        
        // Rollback so changes don't persist
        throw new RuntimeException("Rollback - changes not saved to database");
    }
}
