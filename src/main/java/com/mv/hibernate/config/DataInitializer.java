package com.mv.hibernate.config;

import com.mv.hibernate.OneToMany.OneToManyJDBCExample;
import com.mv.hibernate.OneToMany.OneToManyJPAExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private OneToManyJPAExample jpaExample;

    @Autowired
    private OneToManyJDBCExample jdbcExample;

    @Override
    public void run(String... args) throws Exception {

        jpaExample.getCustomerByPanWithLoanApplications("HIJKL0123O");

    }
}
