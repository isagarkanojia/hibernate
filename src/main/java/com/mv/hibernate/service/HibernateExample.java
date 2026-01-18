package com.mv.hibernate.service;

import com.mv.hibernate.model.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HibernateExample {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Pure Hibernate example: Save a customer using manual transaction management
     */
    public Customer saveCustomer(Customer customer) {
        Session session = null;
        org.hibernate.Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            session.persist(customer);
            transaction.commit();

            System.out.println("Customer saved successfully with ID: " + customer.getId());
            return customer;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error saving customer: " + e.getMessage());
            throw new RuntimeException("Failed to save customer", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /**
     * Pure Hibernate example: Get customer by ID using Session
     */
    public Customer getCustomerById(Long id) {
        Session session = null;

        try {
            session = sessionFactory.openSession();
            Customer customer = session.get(Customer.class, id);

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
            if (session != null) {
                session.close();
            }
        }
    }

    /**
     * Pure Hibernate example: Get customers with risk score > 710 using HQL
     */
    public List<Customer> getCustomersWithHighRiskScore() {
        Session session = null;

        try {
            session = sessionFactory.openSession();
            String hql = "FROM Customer c WHERE c.riskScore > 710";
            List<Customer> customers = session.createQuery(hql, Customer.class).getResultList();

            System.out.println("Found " + customers.size() + " customers with risk score > 710:");
            for (Customer customer : customers) {
                System.out.println("  " + customer);
            }

            return customers;
        } catch (Exception e) {
            System.err.println("Error retrieving customers: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve customers", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /**
     * Demonstrate save and retrieve operations
     */
    public void demonstrateHibernateOperations() {
        System.out.println("\n=== Pure Hibernate Example ===");

        // Create a new customer
        Customer newCustomer = new Customer();
        newCustomer.setName("John Doe (Hibernate)");
        newCustomer.setPan("HIBERNATE001A");
        newCustomer.setDob(new Date()); // Current date
        newCustomer.setRiskScore(650);

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
            System.out.println("✅ Hibernate operations successful!");
        } else {
            System.out.println("❌ Hibernate operations failed!");
        }
    }

}