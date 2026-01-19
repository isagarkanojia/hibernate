package com.mv.hibernate.FirstLevelCache;

import com.mv.hibernate.model.Customer;
import com.mv.hibernate.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Demonstrates that saved entities are immediately put into the first level cache.
 */
@Service
public class SaveAndCacheExample {

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Main demonstration: Save puts entity in cache
     */
    @Transactional
    public void demonstrate() {
        System.out.println("\n=== Save Puts Entity in Cache ===\n");
        
        // Create new customer
        Customer newCustomer = new Customer();
        newCustomer.setName("Test Customer");
        newCustomer.setPan("TESTPAN" + System.currentTimeMillis());
        newCustomer.setDob(new Date());
        newCustomer.setRiskScore(750);
        
        // Save it - will be inserted and cached
        System.out.println("Saving new customer...");
        Customer saved = customerRepository.save(newCustomer);
        System.out.println("Saved with ID: " + saved.getId());
        
        // Fetch by ID - comes from cache, NO DB hit!
        System.out.println("\nFetching saved customer by ID (no DB hit)...");
        Customer fetched = customerRepository.findById(saved.getId()).orElseThrow();
        System.out.println("Fetched: " + fetched.getName());
        
        // Check if same object
        System.out.println("\nSame object? " + (saved == fetched));
        System.out.println("→ Saved entity immediately goes into cache!\n");
        
        // Rollback so we don't clutter the database
        throw new RuntimeException("Rollback - test data not persisted");
    }
}
