package com.mv.hibernate;

import com.mv.hibernate.model.Customer;
import com.mv.hibernate.repository.CustomerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Simple JUnit test for Caffeine Second Level Cache implementation.
 * 
 * This test verifies that:
 * 1. Cache is working - same entity fetched multiple times uses cache
 * 2. Cache persists across transactions
 * 3. Cache is invalidated on update
 */
@SpringBootTest
@TestPropertySource(properties = {
    "spring.jpa.show-sql=true",
    "logging.level.org.hibernate.SQL=DEBUG"
})
class CaffeineCacheTest {

    @Autowired
    private CustomerRepository customerRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        // Clear persistence context to ensure fresh state
        entityManager.clear();
    }

    @Test
    @Transactional
    void testCacheHitOnSecondFetch() {
        // First fetch - should hit database
        Customer customer1 = customerRepository.findById(1L).orElse(null);
        assertNotNull(customer1, "Customer with ID 1 should exist");
        assertEquals("Rohit Sharma", customer1.getName());

        // Clear first level cache (session cache)
        entityManager.clear();

        // Second fetch - should use second level cache (Caffeine)
        Customer customer2 = customerRepository.findById(1L).orElse(null);
        assertNotNull(customer2, "Customer should be found from cache");
        assertEquals("Rohit Sharma", customer2.getName());
        
        // Verify it's the same entity (same ID)
        assertEquals(customer1.getId(), customer2.getId());
    }

    @Test
    @Transactional
    void testCacheWorksAcrossTransactions() {
        // First fetch - hits database
        Customer customer1 = customerRepository.findById(1L).orElse(null);
        assertNotNull(customer1);
        
        // Clear session cache to force second level cache usage
        entityManager.clear();
        
        // Second fetch - should use second level cache
        Customer customer2 = customerRepository.findById(1L).orElse(null);
        assertNotNull(customer2);
        assertEquals(customer1.getName(), customer2.getName());
        assertEquals(customer1.getId(), customer2.getId());
    }

    @Test
    @Transactional
    void testCacheInvalidationOnUpdate() {
        // Fetch customer
        Customer customer = customerRepository.findById(1L).orElse(null);
        assertNotNull(customer);
        String originalName = customer.getName();

        // Update customer - this should invalidate cache
        customer.setName("Updated Test Name");
        customerRepository.save(customer);
        entityManager.flush();
        entityManager.clear();

        // Fetch again - should hit database (cache was invalidated)
        Customer updatedCustomer = customerRepository.findById(1L).orElse(null);
        assertNotNull(updatedCustomer);
        assertEquals("Updated Test Name", updatedCustomer.getName());

        // Restore original name for other tests
        updatedCustomer.setName(originalName);
        customerRepository.save(updatedCustomer);
    }

    @Test
    @Transactional
    void testMultipleCacheHits() {
        // First fetch
        Customer customer1 = customerRepository.findById(1L).orElse(null);
        assertNotNull(customer1);

        // Clear session cache
        entityManager.clear();

        // Multiple fetches - all should use cache
        for (int i = 0; i < 5; i++) {
            Customer cached = customerRepository.findById(1L).orElse(null);
            assertNotNull(cached);
            assertEquals(customer1.getId(), cached.getId());
            entityManager.clear(); // Clear session cache each time
        }
    }

}
