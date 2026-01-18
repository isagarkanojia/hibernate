package com.mv.hibernate.service;

import com.mv.hibernate.model.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class JPAExample {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    /**
     * Pure JPA example: Save a customer using manual transaction management
     */
    public Customer saveCustomer(Customer customer) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        jakarta.persistence.EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(customer);
            transaction.commit();

            System.out.println("Customer saved successfully with ID: " + customer.getId());
            return customer;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error saving customer: " + e.getMessage());
            throw new RuntimeException("Failed to save customer", e);
        } finally {
            entityManager.close();
        }
    }

    /**
     * Pure JPA example: Get customer by ID using EntityManager
     */
    public Customer getCustomerById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            Customer customer = entityManager.find(Customer.class, id);

            if (customer != null) {
                System.out.println("Customer found: " + customer);
            } else {
                System.out.println("Customer with ID " + id + " not found");
            }

            return customer;
        } catch (Exception e) {
            System.err.println("Error retrieving customer: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve customer", e);
        } finally {
            entityManager.close();
        }
    }

    /**
     * Pure JPA example: Get customers with risk score > 710 using JPQL
     */
    public List<Customer> getCustomersWithHighRiskScore() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            String jpql = "SELECT c FROM Customer c WHERE c.riskScore > 710";
            List<Customer> customers = entityManager.createQuery(jpql, Customer.class).getResultList();

            System.out.println("Found " + customers.size() + " customers with risk score > 710:");
            for (Customer customer : customers) {
                System.out.println("  " + customer);
            }

            return customers;
        } catch (Exception e) {
            System.err.println("Error retrieving customers: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve customers", e);
        } finally {
            entityManager.close();
        }
    }

    /**
     * Demonstrate save and retrieve operations using JPA
     */
    public void demonstrateJPAOperations() {
        System.out.println("\n=== Pure JPA Example ===");

        // Create a new customer
        Customer newCustomer = new Customer();
        newCustomer.setName("Jane Smith (JPA)");
        newCustomer.setPan("JPAEXAMPLE001B");
        newCustomer.setDob(new Date()); // Current date
        newCustomer.setRiskScore(720);

        // Save the customer
        Customer savedCustomer = saveCustomer(newCustomer);
        System.out.println("Saved Customer: " + savedCustomer);

        // Retrieve the customer by ID
        Customer retrievedCustomer = getCustomerById(savedCustomer.getId());
        System.out.println("Retrieved Customer: " + retrievedCustomer);

        // Get customers with high risk score
        List<Customer> highRiskCustomers = getCustomersWithHighRiskScore();

        // Verify the data
        if (retrievedCustomer != null &&
            retrievedCustomer.getName().equals(savedCustomer.getName()) &&
            retrievedCustomer.getPan().equals(savedCustomer.getPan())) {
            System.out.println("✅ JPA operations successful!");
        } else {
            System.out.println("❌ JPA operations failed!");
        }
    }
}