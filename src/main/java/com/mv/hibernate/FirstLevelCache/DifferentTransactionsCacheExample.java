package com.mv.hibernate.FirstLevelCache;

import com.mv.hibernate.model.Customer;
import com.mv.hibernate.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Demonstrates that different transactions have their own separate first level cache.
 */
@Service
public class DifferentTransactionsCacheExample {

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Transaction 1: Fetches customer and caches it
     */
    @Transactional(readOnly = true)
    public Customer fetchInTransactionOne(Long customerId) {
        System.out.println("[Transaction 1] Fetching customer " + customerId);
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        System.out.println("[Transaction 1] Found: " + customer.getName());
        return customer;
    }

    /**
     * Transaction 2: Fetches same customer but has its own cache
     */
    @Transactional(readOnly = true)
    public Customer fetchInTransactionTwo(Long customerId) {
        System.out.println("[Transaction 2] Fetching customer " + customerId + " - Will hit DB again!");
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        System.out.println("[Transaction 2] Found: " + customer.getName());
        return customer;
    }

    /**
     * Main demonstration
     */
    public void demonstrate() {
        System.out.println("\n=== Different Transactions = Different Cache ===\n");
        
        // Call first transaction
        Customer customerFromTx1 = fetchInTransactionOne(1L);
        
        // Call second transaction - will hit database again
        Customer customerFromTx2 = fetchInTransactionTwo(1L);
        
        // Compare instances
        System.out.println("\ncompare objects: " + (customerFromTx1 == customerFromTx2));
        System.out.println("→ Different transactions = different cache = different objects\n");
    }
}
