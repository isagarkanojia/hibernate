package com.mv.hibernate.service;

import com.mv.hibernate.model.Customer;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HibernateCriteriaExample {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Hibernate Criteria API example: Save a customer using manual transaction management
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
     * Hibernate Criteria API example: Get customer by ID using CriteriaQuery
     */
    public Customer getCustomerById(Long id) {
        Session session = null;
        try {
            session = sessionFactory.openSession();

            // Create CriteriaBuilder
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

            // Create CriteriaQuery for Customer
            CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);

            // Define the root entity
            Root<Customer> root = criteriaQuery.from(Customer.class);

            // Add WHERE condition: customer.id = :id
            criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));

            // Execute the query
            Customer customer = session.createQuery(criteriaQuery).uniqueResult();

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
     * Hibernate Criteria API example: Get customers with risk score > 710
     * Shows Criteria API with comparison operators and ordering
     */
    public List<Customer> getCustomersWithHighRiskScore() {
        Session session = null;
        try {
            session = sessionFactory.openSession();

            // Create CriteriaBuilder
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

            // Create CriteriaQuery for Customer
            CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);

            // Define the root entity
            Root<Customer> root = criteriaQuery.from(Customer.class);

            // Add WHERE condition: customer.riskScore > 710
            Predicate riskScorePredicate = criteriaBuilder.greaterThan(root.get("riskScore"), 710);
            criteriaQuery.where(riskScorePredicate);

            // Add ORDER BY: sort by riskScore descending, then by name ascending
            criteriaQuery.orderBy(
                criteriaBuilder.desc(root.get("riskScore")),
                criteriaBuilder.asc(root.get("name"))
            );

            // Execute the query
            List<Customer> customers = session.createQuery(criteriaQuery).getResultList();

            System.out.println("Found " + customers.size() + " customers with risk score > 710 (ordered by risk score desc, name asc):");
            for (Customer customer : customers) {
                System.out.println("  " + customer);
            }

            return customers;
        } catch (Exception e) {
            System.err.println("Error retrieving high risk customers: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve high risk customers", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /**
     * Hibernate Criteria API example: Get customers by name pattern using LIKE
     * Shows Criteria API with LIKE operator and case-insensitive search
     */
    public List<Customer> getCustomersByNamePattern(String namePattern) {
        Session session = null;
        try {
            session = sessionFactory.openSession();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
            Root<Customer> root = criteriaQuery.from(Customer.class);

            // Add WHERE condition: customer.name LIKE :pattern (case-insensitive)
            Predicate namePredicate = criteriaBuilder.like(
                criteriaBuilder.lower(root.get("name")),
                "%" + namePattern.toLowerCase() + "%"
            );
            criteriaQuery.where(namePredicate);

            // Order by name
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get("name")));

            List<Customer> customers = session.createQuery(criteriaQuery).getResultList();

            System.out.println("Found " + customers.size() + " customers with name containing '" + namePattern + "':");
            for (Customer customer : customers) {
                System.out.println("  " + customer);
            }

            return customers;
        } catch (Exception e) {
            System.err.println("Error retrieving customers by name pattern: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve customers by name pattern", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /**
     * Hibernate Criteria API example: Count customers in risk score range
     * Shows Criteria API for aggregate queries
     */
    public Long countCustomersInRiskRange(Integer minRisk, Integer maxRisk) {
        Session session = null;
        try {
            session = sessionFactory.openSession();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

            // Create CriteriaQuery for Long (count)
            CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
            Root<Customer> root = criteriaQuery.from(Customer.class);

            // Add WHERE condition: riskScore BETWEEN minRisk AND maxRisk
            Predicate riskRangePredicate = criteriaBuilder.between(root.get("riskScore"), minRisk, maxRisk);
            criteriaQuery.where(riskRangePredicate);

            // Select COUNT(*)
            criteriaQuery.select(criteriaBuilder.count(root));

            Long count = session.createQuery(criteriaQuery).uniqueResult();

            System.out.println("Found " + count + " customers with risk score between " + minRisk + " and " + maxRisk);
            return count;

        } catch (Exception e) {
            System.err.println("Error counting customers in risk range: " + e.getMessage());
            throw new RuntimeException("Failed to count customers in risk range", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /**
     * Demonstrate Hibernate Criteria API operations
     */
    public void demonstrateHibernateCriteriaOperations() {
        System.out.println("\n=== Hibernate Criteria API Example ===");

        // Create a new customer
        Customer newCustomer = new Customer();
        newCustomer.setName("Bob Wilson (Criteria API)");
        newCustomer.setPan("CRITERIA001D");
        newCustomer.setDob(new Date()); // Current date
        newCustomer.setRiskScore(690);

        // Save the customer
        Customer savedCustomer = saveCustomer(newCustomer);
        System.out.println("Saved Customer: " + savedCustomer);

        // Retrieve the customer by ID using Criteria API
        Customer retrievedCustomer = getCustomerById(savedCustomer.getId());
        System.out.println("Retrieved Customer: " + retrievedCustomer);

        // Get customers with high risk score using Criteria API
        List<Customer> highRiskCustomers = getCustomersWithHighRiskScore();

        // Search customers by name pattern
        List<Customer> nameSearchResults = getCustomersByNamePattern("John");

        // Count customers in risk range
        Long countInRange = countCustomersInRiskRange(650, 750);

        // Verify the data
        if (retrievedCustomer != null &&
            retrievedCustomer.getName().equals(savedCustomer.getName()) &&
            retrievedCustomer.getPan().equals(savedCustomer.getPan())) {
            System.out.println("✅ Hibernate Criteria API operations successful!");
        } else {
            System.out.println("❌ Hibernate Criteria API operations failed!");
        }
    }
}