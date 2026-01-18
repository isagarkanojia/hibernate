package com.mv.hibernate.service;

import com.mv.hibernate.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JDBCPreparedStatement {

    private static final Logger logger = LoggerFactory.getLogger(JDBCPreparedStatement.class);

    @Autowired
    private DataSource dataSource;

    /**
     * Get customers with risk score greater than specified value using PreparedStatement
     * SAFE FROM SQL INJECTION - parameters are properly bound
     */
    public List<Customer> getCustomersWithRiskScoreGreaterThan(int riskScore) {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT id, name, pan, dob, risk_score FROM customer WHERE risk_score > ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, riskScore);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Customer customer = extractCustomerFromResultSet(resultSet);
                    customers.add(customer);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching customers with risk score > " + riskScore, e);
        }

        logger.info("Found {} customers with risk score > {}:", customers.size(), riskScore);
        for (Customer customer : customers) {
            logger.info("Customer: {}", customer);
        }
        return customers;
    }



    /**
     * Helper method to extract Customer object from ResultSet
     */
    private Customer extractCustomerFromResultSet(ResultSet resultSet) throws SQLException {
        Customer customer = new Customer();
        customer.setId(resultSet.getLong("id"));
        customer.setName(resultSet.getString("name"));
        customer.setPan(resultSet.getString("pan"));
        customer.setDob(resultSet.getDate("dob"));
        customer.setRiskScore(resultSet.getInt("risk_score"));
        return customer;
    }
}