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
public class JDBCExample {

    private static final Logger logger = LoggerFactory.getLogger(JDBCExample.class);

    @Autowired
    private  DataSource dataSource;

    /**
     * Get users with age greater than 30 using regular Statement
     * NOTE: This approach is vulnerable to SQL injection if the age parameter comes from user input
     */
    public List<User> getUsersOlderThan30() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, name, email, age FROM users WHERE age > 30";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                User user = extractUserFromResultSet(resultSet);
                users.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching users older than 30", e);
        }

        logger.info("Returning {} users older than 30: {}", users.size(), users);
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