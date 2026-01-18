package com.mv.hibernate.config;

import com.mv.hibernate.model.Customer;
import com.mv.hibernate.service.JDBCExample;
import com.mv.hibernate.service.JDBCPreparedStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private JDBCExample jdbcExample;

    @Autowired
    private JDBCPreparedStatement jdbcPreparedStatement;

    @Override
    public void run(String... args) throws Exception {

        // Demonstrate JDBC Example with regular Statement (vulnerable to SQL injection)
        List<Customer> highRiskCustomersExample = jdbcExample.getCustomersWithHighRiskScore();

        // Demonstrate JDBC PreparedStatement (safe from SQL injection)
        List<Customer> highRiskCustomersPrepared = jdbcPreparedStatement.getCustomersWithRiskScoreGreaterThan(710);

    }
}
