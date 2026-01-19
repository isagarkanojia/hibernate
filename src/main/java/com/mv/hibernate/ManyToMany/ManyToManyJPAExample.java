package com.mv.hibernate.ManyToMany;

import com.mv.hibernate.model.CustomerWithProducts;
import com.mv.hibernate.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * MANY-TO-MANY JPA EXAMPLE
 * 
 * This service demonstrates how to fetch a Customer with their Products
 * using JPA (Java Persistence API) with Hibernate
 * 
 * Key Concept:
 * @ManyToMany relationship: Many Customers can have Many Products
 * Uses a junction table (customer_product) to link them
 */
@Service
public class ManyToManyJPAExample {
    
    private static final Logger logger = LoggerFactory.getLogger(ManyToManyJPAExample.class);
    
    @Autowired
    private CustomerWithProductsRepository customerRepository;
    
    /**
     * EXAMPLE 1: Get Customer with All Their Products
     * 
     * How it works:
     * 1. Fetch customer by ID using JPA repository
     * 2. Products are automatically loaded due to @ManyToMany with EAGER fetch
     * 3. Hibernate automatically joins through the junction table
     */
    @Transactional(readOnly = true)
    public void getCustomerWithProducts(Long customerId) {
        logger.info("\n╔═══════════════════════════════════════════════════════╗");
        logger.info("║   MANY-TO-MANY MAPPING DEMONSTRATION (JPA)          ║");
        logger.info("╚═══════════════════════════════════════════════════════╝");
        logger.info("\n========================================");
        logger.info("Example 1: Fetching Customer with Products");
        logger.info("========================================");
        
        // Step 1: Find customer by ID
        Optional<CustomerWithProducts> customerOpt = customerRepository.findById(customerId);
        
        if (customerOpt.isEmpty()) {
            logger.warn("Customer with ID {} not found", customerId);
            return;
        }
        
        CustomerWithProducts customer = customerOpt.get();
        
        logger.info("\nCustomer Details:");
        logger.info("  - ID: {}", customer.getId());
        logger.info("  - Name: {}", customer.getName());
        logger.info("  - PAN: {}", customer.getPan());
        logger.info("  - Risk Score: {}", customer.getRiskScore());
        
        // Step 2: Access products (already loaded automatically)
        List<Product> products = customer.getProducts();
        logger.info("\nNumber of Products: {}", products.size());
        
        // Step 3: Display all products
        logger.info("\nProducts subscribed by Customer: {}", customer.getName());
        logger.info("----------------------------------------");
        for (Product product : products) {
            logger.info("  - ID: {}, Name: {}, Type: {}, Charges: ₹{}", 
                    product.getId(), 
                    product.getName(), 
                    product.getType(), 
                    product.getBaseCharges());
        }
        
        logger.info("========================================\n");
    }
    
    /**
     * EXAMPLE 2: Get All Customers by Product (Reverse Direction)
     * 
     * How it works:
     * 1. Use custom JPQL query to find all customers with a specific product
     * 2. Hibernate joins through junction table from the other direction
     * 3. Shows the Many-to-Many relationship works both ways
     */
    @Transactional(readOnly = true)
    public void getCustomersByProduct(Long productId) {
        logger.info("\n========================================");
        logger.info("Example 2: Fetching All Customers by Product (Reverse)");
        logger.info("========================================");
        
        // Find all customers who have this product
        List<CustomerWithProducts> customers = customerRepository.findCustomersByProductId(productId);
        
        if (customers.isEmpty()) {
            logger.warn("No customers found for Product ID {}", productId);
            return;
        }
        
        logger.info("\nFound {} customer(s) with Product ID: {}", customers.size(), productId);
        
        // Get product name from first customer
        String productName = "Unknown";
        if (!customers.isEmpty() && !customers.get(0).getProducts().isEmpty()) {
            for (Product p : customers.get(0).getProducts()) {
                if (p.getId().equals(productId)) {
                    productName = p.getName();
                    break;
                }
            }
        }
        
        logger.info("\nCustomers subscribed to: {}", productName);
        logger.info("----------------------------------------");
        for (CustomerWithProducts customer : customers) {
            logger.info("  - ID: {}, Name: {}, PAN: {}, Risk Score: {}", 
                    customer.getId(), 
                    customer.getName(), 
                    customer.getPan(),
                    customer.getRiskScore());
        }
        
        logger.info("========================================");
        logger.info("\n✅ Many-to-Many demonstration completed!");
    }
}
