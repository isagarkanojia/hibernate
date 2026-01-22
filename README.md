# Hibernate Training Project

A comprehensive Spring Boot project demonstrating various Hibernate and database concepts with practical examples.

## 📽️ View Presentation

To view the training presentation:
```bash
# Open directly in browser
open presentation.html
# Or double-click presentation.html in file explorer
```

The presentation covers all Hibernate concepts from JDBC to advanced caching with interactive slides.

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Maven 3.6+

### How to Run

#### Option 1: Spring Boot (Recommended)
```bash
mvn spring-boot:run
```
The application starts and automatically runs examples from the `Runner.java` class.

#### Option 2: Command Line Runner
```bash
mvn compile exec:java -Dexec.mainClass="com.mv.hibernate.config.Runner"
```

## 📚 Examples Overview

This project demonstrates Hibernate concepts in progressive order:

### 1. JDBC Examples
**Branch:** `jdbc-example`
- `JDBCExample.java` - Basic JDBC operations
- `JDBCPreparedStatement.java` - PreparedStatement usage

### 2. JPA & Hibernate
**Branch:** `jdbc-hibernate-jpa`
- `JPAExample.java` - Basic JPA operations

### 3. Spring Data JPA
**Branch:** `jpa-spring-data-jpa`
- `SpringDataJPAExample.java` - Spring Data JPA repository usage

### 4. Many-to-One Relationship
**Branch:** `many-to-one`
- `ManyToOneJPAExample.java` - Loan applications belonging to customers
- Demonstrates `@ManyToOne` mapping

### 5. One-to-Many Relationship
**Branch:** `one-to-many`
- `OneToManyJPAExample.java` - Customer with multiple loan applications
- `OneToManyJDBCExample.java` - Same concept using JDBC
- Demonstrates `@OneToMany` mapping

### 6. Many-to-Many Relationship
**Branch:** `many-to-many`
- `ManyToManyJPAExample.java` - Customers with multiple products
- Demonstrates `@ManyToMany` mapping

### 7. First Level Cache
**Branch:** `first-level-cache`
- `FirstLevelCacheExample.java` - Session-level caching
- `SaveAndCacheExample.java` - Save operations and caching
- `UpdatesInCacheExample.java` - Update operations in cache
- `DifferentTransactionsCacheExample.java` - Cache behavior across transactions

### 8. Second Level Cache
**Branch:** `second-level-cache`
- `SecondLevelCacheExample.java` - SessionFactory-level caching with Ehcache

### 9. N+1 Problem
**Branch:** `n-plus-one`
- `NPlusOneProblemDemo.java` - Demonstrates and solves the N+1 query problem

### 10. Inefficient Queries
**Branch:** `inefficiet-query`
- `InefficientSQLExample.java` - Common SQL performance issues and solutions

### 11. Caffeine Cache
**Branch:** `caffiene-l2-cache-generated-by-curser-example`
- Advanced caching implementation using Caffeine

## 🗂️ Project Structure

```
src/main/java/com/mv/hibernate/
├── config/
│   └── Runner.java              # Main runner class
├── service/                     # Core service examples
├── model/                       # JPA entities
├── repository/                  # Spring Data repositories
├── FirstLevelCache/            # L1 cache examples
├── SecondLevelCache/           # L2 cache examples
├── ManyToOne/                   # Many-to-One relationship
├── OneToMany/                   # One-to-Many relationship
├── ManyToMany/                  # Many-to-Many relationship
├── NPlusOne/                    # N+1 problem demo
└── InefficientQuery/           # Query optimization
```

## 🔄 Switching Between Examples

To explore different concepts, checkout the respective branches:

```bash
# JDBC basics
git checkout jdbc-example

# JPA/Hibernate
git checkout jdbc-hibernate-jpa

# Spring Data JPA
git checkout jpa-spring-data-jpa

# Relationships
git checkout many-to-one
git checkout one-to-many
git checkout many-to-many

# Caching
git checkout first-level-cache
git checkout second-level-cache

# Performance issues
git checkout n-plus-one
git checkout inefficiet-query

# Advanced caching
git checkout caffiene-l2-cache-generated-by-curser-example
```

## 🗃️ Database Configuration

- **Database:** H2 (in-memory)
- **Schema:** Auto-created from `schema.sql`
- **Data:** Pre-loaded from `data.sql`
- **Console:** Available at `http://localhost:8080/h2-console`

## 📋 Key Files

- `pom.xml` - Maven dependencies
- `application.properties` - Spring configuration
- `schema.sql` - Database schema
- `data.sql` - Sample data
- `ehcache.xml` - Second level cache configuration

## 🎯 Learning Path

1. **Start with JDBC** - Understand raw database operations
2. **Move to JPA** - Learn ORM basics
3. **Explore Relationships** - One-to-Many, Many-to-One, Many-to-Many
4. **Master Caching** - First level, Second level, Caffeine
5. **Solve Performance Issues** - N+1 problem, inefficient queries

## 🛠️ Running Individual Examples

Modify `Runner.java` to run specific examples:

```java
@Override
public void run(String... args) {
    // Uncomment to run specific examples
    // firstLevelCacheExample.demonstrateCache();
    // secondLevelCacheExample.demonstrateSecondLevelCache();
    // nPlusOneProblemDemo.demonstrateNPlusOneProblem();
}
```

## 📖 Additional Resources

- `HELP.md` - Spring Boot reference documentation
- `HIBERNATE_ADVANCED_TOPICS.md` - Advanced Hibernate concepts
- `PRESENTATION_SLIDES.md` - Training slides
- `QUICK_START.md` - Detailed quick start guide