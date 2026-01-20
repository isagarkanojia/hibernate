package com.mv.hibernate.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class CaffeineCacheConfig {
    // Caffeine caches will be configured automatically by Hibernate
    // through the JCache provider specified in application.properties
}