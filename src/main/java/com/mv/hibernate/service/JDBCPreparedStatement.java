package com.mv.hibernate.service;

import com.mv.hibernate.model.User;
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
     * Get users with age greater than specified value using PreparedStatement
     * SAFE FROM SQL INJECTION - parameters are properly bound
     */
    public List<User> getUsersOlderThan(int age) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, name, email, age FROM users WHERE age > ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, age);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    User user = extractUserFromResultSet(resultSet);
                    users.add(user);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching users older than " + age, e);
        }

        logger.info("Returning {} users older than {}: {}", users.size(), age, users);
        return users;
    }

    /**
     * Helper method to extract User object from ResultSet
     */
    private User extractUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setName(resultSet.getString("name"));
        user.setEmail(resultSet.getString("email"));
        user.setAge(resultSet.getInt("age"));
        return user;
    }
}