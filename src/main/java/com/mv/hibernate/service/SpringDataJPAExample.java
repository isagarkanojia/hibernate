package com.mv.hibernate.service;

import com.mv.hibernate.model.Customer;
import com.mv.hibernate.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SpringDataJPAExample {

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Spring Data JPA example: Save a customer using @Transactional
     * Spring Data JPA automatically manages transactions for save operations
     */
    @Transactional
    public Customer saveCustomer(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        System.out.println("Customer saved successfully with ID: " + savedCustomer.getId());
        return savedCustomer;
    }

    /**
     * Spring Data JPA example: Get customer by ID
     * Uses Spring Data JPA's automatic query generation
     */
    @Transactional(readOnly = true)
    public Customer getCustomerById(Long id) {
        Optional<Customer> customerOpt = customerRepository.findById(id);

        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            System.out.println("Customer found: " + customer);
            return customer;
        } else {
            System.out.println("Customer with ID " + id + " not found");
            return null;
        }
    }

    /**
     * Spring Data JPA example: Get customers with risk score > 710
     * Uses method name-based query generation
     */
    @Transactional(readOnly = true)
    public List<Customer> getCustomersWithHighRiskScore() {
        List<Customer> customers = customerRepository.findByRiskScoreGreaterThan(710);

        System.out.println("Found " + customers.size() + " customers with risk score > 710:");
        for (Customer customer : customers) {
            System.out.println("  " + customer);
        }

        return customers;
    }

    /**
     * Spring Data JPA example: Get customer by PAN using custom method
     */
    @Transactional(readOnly = true)
    public Customer getCustomerByPan(String pan) {
        Customer customer = customerRepository.findByPan(pan);

        if (customer != null) {
            System.out.println("Customer found by PAN: " + customer);
            return customer;
        } else {
            System.out.println("Customer with PAN " + pan + " not found");
            return null;
        }
    }

    /**
     * Spring Data JPA example: Count customers in risk score range
     */
    @Transactional(readOnly = true)
    public long countCustomersInRiskRange(Integer minRisk, Integer maxRisk) {
        long count = customerRepository.countByRiskScoreBetween(minRisk, maxRisk);
        System.out.println("Found " + count + " customers with risk score between " + minRisk + " and " + maxRisk);
        return count;
    }

    /**
     * Demonstrate Spring Data JPA operations
     */
    public void demonstrateSpringDataJPAOperations() {
        System.out.println("\n=== Spring Data JPA Example ===");

        // Create a new customer
        Customer newCustomer = new Customer();
        newCustomer.setName("Alice Johnson (Spring Data JPA)");
        newCustomer.setPan("SPRINGJPA001C");
        newCustomer.setDob(new Date()); // Current date
        newCustomer.setRiskScore(680);

        // Save the customer
        Customer savedCustomer = saveCustomer(newCustomer);
        System.out.println("Saved Customer: " + savedCustomer);

        // Retrieve the customer by ID
        Customer retrievedCustomer = getCustomerById(savedCustomer.getId());
        System.out.println("Retrieved Customer: " + retrievedCustomer);

        // Retrieve by PAN
        Customer customerByPan = getCustomerByPan(savedCustomer.getPan());
        System.out.println("Retrieved by PAN: " + customerByPan);

        // Get customers with high risk score
        List<Customer> highRiskCustomers = getCustomersWithHighRiskScore();

        // Count customers in risk range
//        long countInRange = countCustomersInRiskRange(700, 800);

        // Verify the data
        if (retrievedCustomer != null &&
            retrievedCustomer.getName().equals(savedCustomer.getName()) &&
            retrievedCustomer.getPan().equals(savedCustomer.getPan())) {
            System.out.println("✅ Spring Data JPA operations successful!");
        } else {
            System.out.println("❌ Spring Data JPA operations failed!");
        }
    }
}