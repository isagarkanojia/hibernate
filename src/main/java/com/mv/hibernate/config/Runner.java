package com.mv.hibernate.config;

import com.mv.hibernate.ManyToOne.ManyToOneJPAExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    @Autowired
    private ManyToOneJPAExample manyToOneExample;

    @Override
    public void run(String... args) throws Exception {

        // Demonstrate Many-to-One relationship
        // Many Loan Applications belong to One Customer
        manyToOneExample.getLoanApplicationWithCustomer(1L);

    }
}
