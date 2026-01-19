package com.mv.hibernate.config;

import com.mv.hibernate.ManyToMany.ManyToManyJPAExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    @Autowired
    private ManyToManyJPAExample manyToManyExample;

    @Override
    public void run(String... args) throws Exception {

        // Example 1: Get customer with their products
//        manyToManyExample.getCustomerWithProducts(1L);

        // Example 2: Get all customers who have a specific product (reverse direction)
//        manyToManyExample.getCustomersByProduct(1L);  // Product 1: Premium Savings Account

    }
}
