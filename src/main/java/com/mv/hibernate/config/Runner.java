package com.mv.hibernate.config;

import com.mv.hibernate.OneToMany.OneToManyJDBCExample;
import com.mv.hibernate.OneToMany.OneToManyJPAExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {


    @Autowired
    private OneToManyJPAExample jpaExample;

    @Autowired
    private OneToManyJDBCExample jdbcExample;


    @Override
    public void run(String... args) throws Exception {

        // Customer 1 (Rohit Sharma) - Now has 3 loan applications!
        jpaExample.getCustomerByPanWithLoanApplications("ABCDE1234F");
        jdbcExample.getCustomerByPanWithLoanApplications("ABCDE1234F");

    }
}
