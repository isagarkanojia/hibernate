package com.mv.hibernate.config;

import org.springframework.context.annotation.Configuration;

/**
 * Caffeine Second Level Cache Configuration.
 * 
 * Cache regions are automatically created by Hibernate through the JCache provider
 * specified in application.properties. The 'hibernate.javax.cache.missing_cache_strategy=create'
 * setting ensures missing cache regions are automatically created with default policies,
 * eliminating warnings about missing cache regions.
 * 
 * Cache regions used:
 * - default-query-results-region: For query result caching
 * - default-update-timestamps-region: For tracking entity update timestamps
 * - com.mv.hibernate.model.Customer: Entity cache for Customer (configured via @Cacheable)
 * 
 * For advanced cache configuration (TTL, size limits, etc.), you can create a cache
 * configuration file or configure cache regions programmatically through Hibernate's
 * CacheManager after initialization.
 */
@Configuration
public class CaffeineCacheConfig {
    // Cache regions are automatically managed by Hibernate through JCacheRegionFactory
    // Configuration is done via application.properties
}