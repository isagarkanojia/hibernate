package com.mv.hibernate.config;

import com.mv.hibernate.model.Customer;
import com.mv.hibernate.service.HibernateExample;
import com.mv.hibernate.service.JDBCExample;
import com.mv.hibernate.service.JDBCPreparedStatement;
import com.mv.hibernate.service.JPAExample;
import com.mv.hibernate.service.SpringDataJPAExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Runner implements CommandLineRunner {

    @Autowired
    private JDBCExample jdbcExample;

    @Autowired
    private JDBCPreparedStatement jdbcPreparedStatement;

    @Autowired
    private HibernateExample hibernateExample;

    @Autowired
    private JPAExample jpaExample;

    @Autowired
    private SpringDataJPAExample springDataJPAExample;

    @Override
    public void run(String... args) throws Exception {

        // Demonstrate JDBC Example with regular Statement (vulnerable to SQL injection)
        List<Customer> highRiskCustomersExample = jdbcExample.getCustomersWithHighRiskScore();

        // Demonstrate JDBC PreparedStatement (safe from SQL injection)
        List<Customer> highRiskCustomersPrepared = jdbcPreparedStatement.getCustomersWithRiskScoreGreaterThan(710);

        // Demonstrate Pure Hibernate operations (save and get by id)
        hibernateExample.demonstrateHibernateOperations();

        // Demonstrate Pure JPA operations (save and get by id)
        jpaExample.demonstrateJPAOperations();

        // Demonstrate Spring Data JPA operations (highest level abstraction)
        springDataJPAExample.demonstrateSpringDataJPAOperations();

    }
}
