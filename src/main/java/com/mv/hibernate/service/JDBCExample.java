package com.mv.hibernate.service;

import com.mv.hibernate.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JDBCExample {

    private static final Logger logger = LoggerFactory.getLogger(JDBCExample.class);

    @Autowired
    private  DataSource dataSource;

    /**
     * Get customers with risk score greater than 710 using regular Statement
     * NOTE: This approach is vulnerable to SQL injection if parameters come from user input
     */
    public List<Customer> getCustomersWithHighRiskScore() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT id, name, pan, dob, risk_score FROM customer WHERE risk_score > 710";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Customer customer = extractCustomerFromResultSet(resultSet);
                customers.add(customer);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching customers with high risk score", e);
        }

        logger.info("Found {} customers with risk score > 710:", customers.size());
        for (Customer customer : customers) {
            logger.info("Customer: {}", customer);
        }
        return customers;
    }

    /**
     * Get customer by id using regular Statement
     * NOTE: This approach is vulnerable to SQL injection if id comes from user input
     */
    public Optional<Customer> getCustomerById(Long id) {
        String sql = "SELECT id, name, pan, dob, risk_score FROM customer WHERE id = " + id;

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            if (resultSet.next()) {
                Customer customer = extractCustomerFromResultSet(resultSet);
                logger.info("Found customer with id {}: {}", id, customer);
                return Optional.of(customer);
            } else {
                logger.info("No customer found with id {}", id);
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching customer with id " + id, e);
        }
    }

    /**
     * Save customer using regular Statement
     * NOTE: This approach is vulnerable to SQL injection if customer data comes from user input
     */
    public Customer saveCustomer(Customer customer) {
        String sql = "INSERT INTO customer (name, pan, dob, risk_score) VALUES ('" +
                     customer.getName() + "', '" +
                     customer.getPan() + "', '" +
                     customer.getDob() + "', " +
                     customer.getRiskScore() + ")";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            int rowsAffected = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        customer.setId(generatedKeys.getLong(1));
                        logger.info("Saved customer with generated id {}: {}", customer.getId(), customer);
                    }
                }
            }

            return customer;

        } catch (SQLException e) {
            throw new RuntimeException("Error saving customer: " + customer, e);
        }
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