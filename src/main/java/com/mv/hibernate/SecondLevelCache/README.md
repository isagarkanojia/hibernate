# Hibernate Second-Level Cache with Caffeine

This implementation demonstrates Hibernate's second-level cache using **Caffeine** as the cache provider.

## Overview

**Second-level cache** is a shared cache across all sessions in a Hibernate application. Unlike first-level cache (session-scoped), second-level cache persists across transactions and is shared among all users of the application.

## Key Differences from First-Level Cache

| Feature | First-Level Cache | Second-Level Cache |
|---------|-------------------|-------------------|
| Scope | Single Session/Transaction | All Sessions/Transactions |
| Lifetime | Transaction duration | Application lifetime |
| Shared | No | Yes |
| Configuration | Automatic | Requires setup |

## Caffeine Cache Provider

**Caffeine** is a high-performance, near-optimal caching library for Java. Key benefits:

- **High Performance**: Generally faster than Ehcache for most use cases
- **Modern API**: Contemporary caching approach
- **Rich Statistics**: Detailed cache metrics and monitoring
- **Active Development**: Regular updates and improvements

## Implementation Details

### 1. Dependencies (pom.xml)
```xml
<!-- Hibernate JCache integration -->
<dependency>
    <groupId>org.hibernate.orm</groupId>
    <artifactId>hibernate-jcache</artifactId>
</dependency>

<!-- Caffeine with JCache support -->
<dependency>
    <groupId>com.github.ben-manes.caffeine</groupId>
    <artifactId>caffeine</artifactId>
</dependency>
<dependency>
    <groupId>com.github.ben-manes.caffeine</groupId>
    <artifactId>jcache</artifactId>
</dependency>
```

### 2. Configuration (application.properties)
```properties
# Enable second-level cache
spring.jpa.properties.hibernate.cache.use_second_level_cache=true
spring.jpa.properties.hibernate.cache.use_query_cache=true

# Use JCache region factory
spring.jpa.properties.hibernate.cache.region.factory_class=org.hibernate.cache.jcache.JCacheRegionFactory

# Specify Caffeine as JCache provider
spring.jpa.properties.hibernate.javax.cache.provider=com.github.benmanes.caffeine.jcache.spi.CaffeineCachingProvider
```

### 3. Entity Annotations
```java
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Customer {
    // Entity fields...
}
```

### 4. Cache Regions

Three cache regions are automatically created:

1. **Entity Cache**: `com.mv.hibernate.model.Customer`
   - Caches individual entity instances
   - 1-hour TTL (Time To Live)

2. **Query Cache**: `default-query-results-region`
   - Caches query results
   - 10-minute TTL

3. **Update Timestamps**: `default-update-timestamps-region`
   - Tracks entity update timestamps for cache invalidation
   - Never expires (required for cache consistency)

## Cache Behavior Demonstration

The `SecondLevelCacheExample` demonstrates:

1. **Cache Hit**: Second fetch of same entity doesn't hit database
2. **Cache Sharing**: Different transactions share the same cache
3. **Cache Invalidation**: Updates automatically clear affected cache entries
4. **Cache Persistence**: Cache survives across different method calls

## Monitoring Cache Performance

Look for these patterns in the SQL logs:

- **First fetch**: SQL query executed
- **Subsequent fetches**: No SQL query (cache hit)
- **After update**: SQL query executed again (cache invalidated)

## Cache Strategies

| Strategy | Read | Write | Use Case |
|----------|------|-------|----------|
| READ_ONLY | ✅ | ❌ | Immutable data |
| READ_WRITE | ✅ | ✅ | Read-heavy with updates |
| NONSTRICT_READ_WRITE | ✅ | ✅ | High concurrency |
| TRANSACTIONAL | ✅ | ✅ | JTA environments |

## Performance Benefits

- **Reduced Database Load**: Fewer queries for frequently accessed data
- **Faster Response Times**: Cached data retrieval is much faster
- **Scalability**: Better performance under load
- **Resource Efficiency**: Less CPU and memory usage on database

## When to Use Second-Level Cache

**Good candidates:**
- Read-heavy applications
- Frequently accessed reference data
- Small to medium datasets
- Applications with performance bottlenecks

**Not suitable for:**
- Write-heavy applications
- Large datasets that don't fit in memory
- Real-time data requirements
- Distributed cache needs (use Redis/Hazelcast)

## Alternative Cache Providers

- **Ehcache**: Mature, feature-rich, good for enterprise
- **Redis**: Distributed caching, microservices-friendly
- **Hazelcast**: Distributed, clustering support
- **Infinispan**: Enterprise-grade distributed caching

## Troubleshooting

**Common Issues:**
- Cache regions not found warnings (can be ignored or configured explicitly)
- Statistics not available (expected with JCache abstraction)
- Performance not improving (check cache hit ratios)

**Debug Tips:**
- Enable SQL logging to monitor cache hits/misses
- Use Hibernate statistics: `hibernate.generate_statistics=true`
- Monitor memory usage under load

## Next Steps

1. Enable query cache hints in repository methods
2. Configure cache regions explicitly for production
3. Monitor cache performance in production
4. Consider cache clustering for multi-instance deployments