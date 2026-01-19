package com.mv.hibernate.FirstLevelCache;

import com.mv.hibernate.model.Customer;
import com.mv.hibernate.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FirstLevelCacheExample {

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Simple demonstration of First Level Cache in Spring Data JPA.
     * Within the same transaction, fetching the same entity multiple times
     * will only hit the database once.
     */
    @Transactional(readOnly = true)
    public void demonstrateFirstLevelCache() {
        System.out.println("\n=== First Level Cache Demo ===\n");
        
        // First fetch - will execute SQL query
        System.out.println("1. First fetch - will hit database:");
        Customer customer1 = customerRepository.findById(1L).orElse(null);
        System.out.println("   Found: " + customer1.getName());
        
        // Second fetch - will NOT execute SQL (returns from cache)
        System.out.println("\n2. Second fetch - will NOT hit database (cached):");
        Customer customer2 = customerRepository.findById(1L).orElse(null);
        System.out.println("   Found: " + customer2.getName());
        
        // Third fetch - will NOT execute SQL (returns from cache)
        System.out.println("\n3. Third fetch - will NOT hit database (cached):");
        Customer customer3 = customerRepository.findById(1L).orElse(null);
        System.out.println("   Found: " + customer3.getName());
        
        // Verify all three references point to the same object
        System.out.println("\n4. Checking if all references are the same object:");
        System.out.println("   customer1 == customer2: " + (customer1 == customer2));
        System.out.println("   customer2 == customer3: " + (customer2 == customer3));
        System.out.println("   → All three are the SAME object from cache!\n");
    }
}
