package com.mv.hibernate.config;

import com.mv.hibernate.model.Customer;
import com.mv.hibernate.model.CustomerWithLoans;
import com.mv.hibernate.model.LoanApplication;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Component
public class HibernateConfig {

    @Bean
    public SessionFactory sessionFactory() {
        Configuration configuration = new Configuration();

        // Database connection properties
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:testdb");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.username", "sa");
        configuration.setProperty("hibernate.connection.password", "");
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");

        // Hibernate properties
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.format_sql", "true");
        configuration.setProperty("hibernate.highlight_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "validate"); // Use validate since we have schema.sql
        configuration.setProperty("hibernate.current_session_context_class", "thread");

        // Add annotated classes
        configuration.addAnnotatedClass(Customer.class);
        configuration.addAnnotatedClass(CustomerWithLoans.class);
        configuration.addAnnotatedClass(LoanApplication.class);

        return configuration.buildSessionFactory();
    }

    @Bean
    @Primary
    public EntityManagerFactory entityManagerFactory(SessionFactory sessionFactory) {
        return sessionFactory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(sessionFactory);
        return transactionManager;
    }
}