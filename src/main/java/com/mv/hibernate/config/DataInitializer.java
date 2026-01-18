package com.mv.hibernate.config;

import com.mv.hibernate.model.User;
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


        List<User> olderUsersExample = jdbcExample.getUsersOlderThan30();

        List<User> olderUsersPrepared = jdbcPreparedStatement.getUsersOlderThan(30);


    }
}
