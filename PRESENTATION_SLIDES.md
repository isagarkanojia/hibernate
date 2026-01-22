# Mastering JDBC, JPA & Hibernate
## From Raw SQL to Modern ORM

**Presented by: Sagar Kanojia**  
**2026**

---

## SLIDE 1: Title Slide

# Mastering JDBC, JPA & Hibernate
### From Raw SQL to Modern ORM

**Presented by: Sagar Kanojia**  
**2026**

A Deep Dive into Java Persistence

---

## SLIDE 2: What is JDBC?

### What is JDBC?

**JDBC (Java Database Connectivity)**
- Java API for connecting to relational databases
- Provides methods to query and update data
- Database-independent abstraction layer
- Part of Java SE (java.sql package)

**Key Components:**
- **DriverManager**: Manages database drivers
- **Connection**: Represents database connection
- **Statement/PreparedStatement**: Executes SQL queries
- **ResultSet**: Holds query results

---

## SLIDE 3: JDBC Architecture

### JDBC Architecture Diagram

```
┌─────────────────────────────────────────┐
│       Java Application                  │
└─────────────┬───────────────────────────┘
              │
              ▼
┌─────────────────────────────────────────┐
│       JDBC API (java.sql.*)             │
│  DriverManager | Connection             │
│  Statement | PreparedStatement          │
│  ResultSet | SQLException               │
└─────────────┬───────────────────────────┘
              │
              ▼
┌─────────────────────────────────────────┐
│       JDBC Driver Manager               │
└─────────────┬───────────────────────────┘
              │
     ┌────────┼────────┐
     ▼        ▼        ▼
┌────────┐ ┌──────┐ ┌──────────┐
│ MySQL  │ │PostgreSQL│ │ Oracle │
│ Driver │ │ Driver │ │ Driver │
└────┬───┘ └───┬──┘ └────┬─────┘
     │         │         │
     ▼         ▼         ▼
┌──────────────────────────────────┐
│       Database Servers           │
└──────────────────────────────────┘
```

**Flow:**
1. Application uses JDBC API
2. DriverManager selects appropriate driver
3. Driver translates calls to database-specific protocol
4. Database executes and returns results

---

## SLIDE 4: JDBC Example - Statement (⚠️ Not Safe)

### JDBC Example - Statement (⚠️ Not Safe)
**Direct SQL execution - vulnerable to SQL injection**

```java
// ❌ Vulnerable to SQL Injection
public List<Customer> getCustomersWithHighRiskScore() {
    List<Customer> customers = new ArrayList<>();
    String sql = "SELECT id, name, pan, dob, risk_score " +
                 "FROM customer WHERE risk_score > 710";

    try (Connection conn = dataSource.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        while (rs.next()) {
            Customer customer = new Customer();
            customer.setId(rs.getLong("id"));
            customer.setName(rs.getString("name"));
            customer.setPan(rs.getString("pan"));
            customer.setDob(rs.getDate("dob"));
            customer.setRiskScore(rs.getInt("risk_score"));
            customers.add(customer);
        }
    } catch (SQLException e) {
        throw new RuntimeException("Database error", e);
    }
    return customers;
}
```

**Problems:**
- ❌ SQL Injection vulnerability
- ❌ String concatenation for dynamic queries
- ❌ Manual ResultSet to Object mapping
- ❌ Boilerplate code for resource management

---

## SLIDE 5: JDBC Example - PreparedStatement (✅ Safe)

### JDBC Example - PreparedStatement (✅ Safe)
**Parameterized queries prevent SQL injection**

```java
// ✅ Safe from SQL Injection
public List<Customer> getCustomersWithRiskScore(int riskScore) {
    List<Customer> customers = new ArrayList<>();
    String sql = "SELECT id, name, pan, dob, risk_score " +
                 "FROM customer WHERE risk_score > ?";

    try (Connection conn = dataSource.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setInt(1, riskScore);  // Parameter binding

        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getLong("id"));
                customer.setName(rs.getString("name"));
                // ... more mapping
                customers.add(customer);
            }
        }
    } catch (SQLException e) {
        throw new RuntimeException("Database error", e);
    }
    return customers;
}
```

**Advantages:**
- ✅ SQL injection protection
- ✅ Better performance (query caching)
- ✅ Type-safe parameter binding

**Still Problems:**
- ❌ Manual object mapping
- ❌ Repetitive code
- ❌ No relationship management

---

## SLIDE 6: Problems with JDBC

### Problems with JDBC

**1. Boilerplate Code**
- Connection management
- Statement preparation
- Resource cleanup (try-catch-finally)

**2. Manual Object Mapping**
- ResultSet → Object conversion
- Object → SQL parameters conversion
- Type conversions (Date, Enums, etc.)

**3. No Relationship Management**
- Manual foreign key handling
- Complex JOIN queries for related data
- No lazy loading

**4. No Caching**
- Every query hits database
- No automatic optimization

---

## SLIDE 7: The Need for ORM

### The Need for ORM (Object-Relational Mapping)

**What is ORM?**
- Maps Java objects to database tables automatically
- Eliminates manual SQL for common operations
- Manages relationships between entities

**Benefits:**
- ✅ Reduced boilerplate code (70-80% less code)
- ✅ Automatic object-relational mapping
- ✅ Built-in caching (performance)
- ✅ Lazy/Eager loading strategies
- ✅ Transaction management
- ✅ Database independence (portable)
- ✅ Relationship management (1-1, 1-M, M-M)

**Popular Java ORMs:**
- Hibernate (most popular)
- EclipseLink
- TopLink

---

## SLIDE 8: Introduction to Hibernate

### Introduction to Hibernate

**What is Hibernate?**
- Most popular Java ORM framework
- Open-source (Red Hat)
- Implements JPA specification (plus extensions)

**Key Features:**
- Automatic table creation from entities
- HQL (Hibernate Query Language) - object-oriented SQL
- First-level & Second-level caching
- Lazy loading of associations
- Dirty checking & automatic updates
- Batch processing
- Database dialect abstraction

**Hibernate = JPA + Extra Features**
- JPA: Standard specification
- Hibernate: Implementation + additional features

---

## SLIDE 9: Hibernate Architecture

### Hibernate Architecture

```
┌──────────────────────────────────────────────┐
│         Java Application Layer               │
│      (Entity Classes: Customer.java)         │
└──────────────┬───────────────────────────────┘
               │
               ▼
┌──────────────────────────────────────────────┐
│         JPA/Hibernate API                    │
│  EntityManager | Session | Query             │
└──────────────┬───────────────────────────────┘
               │
               ▼
┌──────────────────────────────────────────────┐
│         Persistence Context                  │
│      (First-Level Cache - Session)           │
└──────────────┬───────────────────────────────┘
               │
    ┌──────────┼──────────┐
    ▼          ▼          ▼
┌────────┐ ┌───────┐ ┌──────────┐
│ Query  │ │ Second│ │ Trans    │
│ Cache  │ │ Level │ │ Manager  │
│        │ │ Cache │ │          │
└────┬───┘ └───┬───┘ └────┬─────┘
     │         │          │
     └─────────┼──────────┘
               ▼
┌──────────────────────────────────────────────┐
│            JDBC Layer                        │
└──────────────┬───────────────────────────────┘
               ▼
┌──────────────────────────────────────────────┐
│            Database                          │
└──────────────────────────────────────────────┘
```

**Persistence Context (First-Level Cache):**
- Tracks all entities in current session/transaction
- Automatic dirty checking
- Identity guarantee (single object per ID)

---

## SLIDE 10: Introduction to JPA

### Introduction to JPA (Jakarta Persistence API)

**What is JPA?**
- Standard specification for ORM in Java
- Part of Jakarta EE (formerly Java EE)
- Vendor-neutral API

**JPA is a Specification, Not Implementation**

**Implementations:**
- Hibernate (most popular)
- EclipseLink (reference implementation)
- OpenJPA

**Key Interfaces:**
- **EntityManagerFactory** - creates EntityManagers
- **EntityManager** - main API for persistence operations
- **EntityTransaction** - transaction management
- **Query/TypedQuery** - JPQL queries

**Benefit: Portability**
- Switch ORM vendors without code changes
- Standard annotations (@Entity, @Table, etc.)

---

## SLIDE 11: JPA vs Hibernate

### JPA vs Hibernate

```
┌────────────────────────┬─────────────────────────────┐
│      JPA               │      Hibernate              │
├────────────────────────┼─────────────────────────────┤
│ Specification          │ Implementation              │
│ jakarta.persistence.*  │ org.hibernate.*             │
│ Standard annotations   │ Extra annotations available │
│ EntityManager          │ Session (native API)        │
│ JPQL queries           │ HQL (superset of JPQL)      │
│ Limited caching        │ Advanced 2nd-level cache    │
│ Basic features         │ Advanced features           │
└────────────────────────┴─────────────────────────────┘
```

**Hibernate-Specific Features:**
- `@Formula` - computed properties
- `@Cache` - fine-grained cache control
- `@BatchSize` - batch fetching optimization
- `@Filter` - dynamic data filtering
- Statistics API

**Best Practice: Use JPA + Hibernate extras when needed**

---

## SLIDE 12: Introduction to Spring Data JPA

### Introduction to Spring Data JPA

**What is Spring Data JPA?**
- Spring abstraction layer over JPA
- Eliminates even more boilerplate
- Repository pattern implementation

**Key Features:**
- ✅ Auto-implemented repositories (no impl needed!)
- ✅ Query methods from method names
- ✅ @Query annotation for custom JPQL/SQL
- ✅ Pagination & sorting built-in
- ✅ Automatic transaction management (@Transactional)
- ✅ No EntityManager boilerplate

**Example:**
```java
public interface CustomerRepository 
       extends JpaRepository<Customer, Long> {
    
    // Method name → Query generation!
    List<Customer> findByRiskScoreGreaterThan(int score);
    Customer findByPan(String pan);
    long countByRiskScoreBetween(int min, int max);
}
```

---

## SLIDE 13: Evolution - Same Task, Different Approaches

### Evolution: JDBC → JPA → Spring Data JPA
**Same Task - Different Approaches**

**Task: Find Customer by ID**

**JDBC (15+ lines):**
```java
Connection conn = dataSource.getConnection();
PreparedStatement pstmt = conn.prepareStatement(
    "SELECT * FROM customer WHERE id = ?");
pstmt.setLong(1, id);
ResultSet rs = pstmt.executeQuery();
Customer customer = null;
if (rs.next()) {
    customer = new Customer();
    customer.setId(rs.getLong("id"));
    customer.setName(rs.getString("name"));
    // ... more fields
}
// ... cleanup (finally blocks)
```

**JPA (5 lines):**
```java
EntityManager em = emf.createEntityManager();
Customer customer = em.find(Customer.class, id);
em.close();
```

**Spring Data JPA (1 line!):**
```java
Customer customer = customerRepository.findById(id).orElse(null);
```

---

## SLIDE 14: JPA Entity Annotations

### JPA Entity Annotations
**From Your Customer Entity**

```java
@Entity                              // Marks as JPA entity
@Table(name = "customer")            // Maps to table
@Cacheable                           // Enables 2nd level cache
public class Customer {

    @Id                              // Primary key
    @GeneratedValue(strategy = IDENTITY) // Auto-increment
    private Long id;

    @Column(name = "name",           // Column mapping
            nullable = false,        // NOT NULL constraint
            length = 100)            // VARCHAR(100)
    private String name;

    @Column(name = "pan", 
            unique = true)           // UNIQUE constraint
    private String pan;

    @Column(name = "dob")
    @Temporal(TemporalType.DATE)     // Only date, no time
    private Date dob;

    @Column(name = "risk_score")
    private Integer riskScore;

    // Constructors, getters, setters...
}
```

**Common JPA Annotations:**
- `@Entity`, `@Table` - class/table mapping
- `@Id`, `@GeneratedValue` - primary key
- `@Column` - field/column mapping
- `@Temporal` - date/time precision
- `@OneToMany`, `@ManyToOne`, `@ManyToMany` - relationships
- `@JoinColumn` - foreign key

---

## SLIDE 15: JPQL, HQL, and Criteria API

### JPQL, HQL, and Criteria API

**JPQL (Java Persistence Query Language)**
- Object-oriented query language (not SQL!)
- Queries entities, not tables

```java
// JPQL - queries Customer entity (not table)
SELECT c FROM Customer c WHERE c.riskScore > 710
```

**HQL (Hibernate Query Language)**
- Hibernate's superset of JPQL
- Additional features (formulas, etc.)

**Native SQL**
```java
@Query(value = "SELECT * FROM customer "+
               "WHERE risk_score > ?", 
       nativeQuery = true)
```

**Criteria API (Type-Safe Queries)**
```java
CriteriaBuilder cb = em.getCriteriaBuilder();
CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
Root<Customer> customer = cq.from(Customer.class);
cq.where(cb.gt(customer.get("riskScore"), 710));
```

---

## SLIDE 16: Entity Relationship Mappings

### Entity Relationship Mappings

**Three Main Relationship Types:**

**1. @OneToMany (1:N)**
- One Customer → Many LoanApplications
- Parent side defines collection

```java
@OneToMany(fetch = LAZY)
@JoinColumn(name = "customer_id")
List<LoanApplication> loanApplications;
```

**2. @ManyToOne (N:1)**
- Many LoanApplications → One Customer
- Child side defines reference

```java
@ManyToOne
@JoinColumn(name = "customer_id")
CustomerWithLoans customer;
```

**3. @ManyToMany (M:N)**
- Many Customers → Many Products
- Requires join table

```java
@ManyToMany
@JoinTable(name = "customer_products",
  joinColumns = @JoinColumn(name = "customer_id"),
  inverseJoinColumns = @JoinColumn(name = "product_id"))
List<Product> products;
```

---

## SLIDE 17: One-to-Many Mapping Example

### One-to-Many Mapping Example
**Customer → Loan Applications (from your code)**

**Database Tables:**
```
┌─────────────────────┐       ┌──────────────────────┐
│    CUSTOMER         │       │  LOAN_APPLICATION    │
├─────────────────────┤       ├──────────────────────┤
│ id (PK)             │◄──────┤ customer_id (FK)     │
│ name                │  1:N  │ id (PK)              │
│ pan                 │       │ product_type         │
│ dob                 │       │ requested_amount     │
│ risk_score          │       │ status               │
└─────────────────────┘       └──────────────────────┘
```

**Java Entities:**
```
CustomerWithLoans              LoanApplication
  ├─ id                          ├─ id
  ├─ name                        ├─ customerId
  ├─ pan                         ├─ productType
  └─ loanApplications: List      ├─ requestedAmount
                                 └─ status
```

**Code:**
```java
@Entity
@Table(name = "customer")
public class CustomerWithLoans {
    @Id
    private Long id;
    private String name;
    
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private List<LoanApplication> loanApplications;
}
```

---

## SLIDE 18: Many-to-One Mapping Example

### Many-to-One Mapping Example
**Loan Application → Customer (inverse relationship)**

**Database Tables:**
```
┌──────────────────────┐       ┌─────────────────────┐
│  LOAN_APPLICATION    │       │    CUSTOMER         │
├──────────────────────┤       ├─────────────────────┤
│ id (PK)              │       │ id (PK)             │
│ customer_id (FK)     ├──────►│ name                │
│ product_type         │  N:1  │ pan                 │
│ requested_amount     │       │ dob                 │
│ status               │       │ risk_score          │
└──────────────────────┘       └─────────────────────┘
```

**Code:**
```java
@Entity
@Table(name = "loan_application")
public class LoanApplicationWithCustomer {
    @Id
    private Long id;
    private String productType;
    private BigDecimal requestedAmount;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerWithLoans customer;
}
```

**Use Case:**
- When you primarily query from the loan side
- Need customer details for each loan
- Example: Loan approval workflow

---

## SLIDE 19: Many-to-Many Mapping Example

### Many-to-Many Mapping Example
**Customer ↔ Products (from your code)**

**Database Tables:**
```
┌──────────────┐    ┌───────────────────┐    ┌──────────────┐
│   CUSTOMER   │    │ CUSTOMER_PRODUCTS │    │   PRODUCT    │
├──────────────┤    ├───────────────────┤    ├──────────────┤
│ id (PK)      │◄───┤ customer_id (FK)  │    │ id (PK)      │
│ name         │    │ product_id (FK)   ├───►│ name         │
│ pan          │    └───────────────────┘    │ category     │
└──────────────┘                             │ description  │
                                             └──────────────┘
```

**Code:**
```java
@Entity
public class CustomerWithProducts {
    @Id
    private Long id;
    
    @ManyToMany
    @JoinTable(
        name = "customer_products",
        joinColumns = @JoinColumn(name = "customer_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;
}

@Entity
public class Product {
    @Id
    private Long id;
    
    @ManyToMany(mappedBy = "products")
    private List<CustomerWithProducts> customers;
}
```

**Key Points:**
- Requires intermediate join table
- Both sides can be collection owners
- Use `mappedBy` on inverse side

---

## SLIDE 20: Hibernate Caching Overview

### Hibernate Caching Overview

**Why Caching?**
- Reduce database round trips
- Improve application performance
- Lower database load

**Two Levels of Cache in Hibernate:**

```
┌────────────────────────────────────────────────┐
│  First-Level Cache (Session/Transaction)      │
│  • Enabled by default                          │
│  • Transaction-scoped                          │
│  • Cannot be disabled                          │
└────────────────────────────────────────────────┘
                    ▼
┌────────────────────────────────────────────────┐
│  Second-Level Cache (Application-wide)        │
│  • Optional, must be enabled                   │
│  • Shared across sessions                      │
│  • Requires external provider (EhCache)        │
└────────────────────────────────────────────────┘
                    ▼
┌────────────────────────────────────────────────┐
│            Database                            │
└────────────────────────────────────────────────┘
```

---

## SLIDE 21: First-Level Cache (L1)

### First-Level Cache (L1)
**Session/Transaction-Scoped Cache**

**Characteristics:**
- ✅ Enabled by default (cannot disable)
- ✅ Lives within @Transactional boundary
- ✅ Automatic dirty checking
- ✅ Identity guarantee (same object reference)

**Key Behaviors (from your examples):**

**1. Cache Hits Within Transaction**
```java
@Transactional
public void demo() {
    Customer c1 = repo.findById(1L).get(); // DB hit
    Customer c2 = repo.findById(1L).get(); // Cache hit!
    Customer c3 = repo.findById(1L).get(); // Cache hit!
    // c1 == c2 == c3 → true (same object!)
}
```

**2. Different Transactions = Different Cache**
```java
@Transactional
void tx1() { Customer c = repo.findById(1L); } // DB hit

@Transactional
void tx2() { Customer c = repo.findById(1L); } // DB hit again
```

**3. Dirty Checking (Automatic Updates)**
```java
@Transactional
void update() {
    Customer c = repo.findById(1L);
    c.setName("Changed");  // No save() needed!
    // Hibernate detects change & updates on commit
}
```

---

## SLIDE 22: Second-Level Cache (L2)

### Second-Level Cache (L2)
**Application-Wide Shared Cache**

**Characteristics:**
- Optional (must enable explicitly)
- Shared across ALL sessions/transactions
- Survives transaction boundaries
- Requires external provider (EhCache, Hazelcast)

**Configuration Steps (from your project):**

**1. Add Dependencies**
```xml
<dependency>
    <groupId>org.hibernate.orm</groupId>
    <artifactId>hibernate-jcache</artifactId>
</dependency>
<dependency>
    <groupId>org.ehcache</groupId>
    <artifactId>ehcache</artifactId>
</dependency>
```

**2. Enable in application.properties**
```properties
spring.jpa.properties.hibernate.cache.use_second_level_cache=true
spring.jpa.properties.hibernate.cache.region.factory_class=
    org.hibernate.cache.jcache.JCacheRegionFactory
```

**3. Annotate Entities**
```java
@Entity
@Cacheable  // JPA standard
public class Customer { ... }
```

**Result:**
- First transaction: DB hit → stored in L2
- Subsequent transactions: L2 cache hit (no DB!)

---

## SLIDE 23: Common Pitfall #1 - N+1 Problem

### Common Pitfall #1: N+1 Problem
**The Silent Performance Killer**

**The Problem:**
Fetching N entities triggers 1 + N database queries

**Example (from your code):**
```java
// Query 1: Fetch all customers
List<CustomerWithLoans> customers = repo.findAll();

// Query 2-N: Fetch loans for EACH customer
for (CustomerWithLoans customer : customers) {
    customer.getLoanApplications().size(); // Lazy load!
    // → Separate query for EACH customer!
}
```

**SQL Generated:**
```sql
SELECT * FROM customer;                    -- 1 query
SELECT * FROM loan_application WHERE customer_id = 1;  -- Query 2
SELECT * FROM loan_application WHERE customer_id = 2;  -- Query 3
SELECT * FROM loan_application WHERE customer_id = 3;  -- Query 4
-- ... N more queries!
```

**Impact:**
- ❌ 10 customers = 11 queries
- ❌ 1000 customers = 1001 queries
- ❌ Exponential performance degradation

---

## SLIDE 24: N+1 Problem - Solutions

### N+1 Problem - Solutions

**Solution 1: JOIN FETCH (Recommended)**
```java
@Query("SELECT DISTINCT c FROM CustomerWithLoans c " +
       "LEFT JOIN FETCH c.loanApplications")
List<CustomerWithLoans> findAllWithLoans();
```
- ✅ Single query with JOIN
- ✅ All data loaded eagerly
- ✅ Result: 1 query total (not 1+N)

**Solution 2: @EntityGraph**
```java
@EntityGraph(attributePaths = {"loanApplications"})
List<CustomerWithLoans> findAll();
```
- ✅ More declarative
- ✅ Can specify multiple paths

**Solution 3: @BatchSize**
```java
@OneToMany
@BatchSize(size = 10)  // Load 10 at once
List<LoanApplication> loanApplications;
```
- ⚠️ Reduces queries but doesn't eliminate problem
- ✅ Good compromise for complex scenarios

**Performance Comparison:**
- No optimization: 1001 queries (10 customers)
- JOIN FETCH: 1 query
- @BatchSize(10): ~2 queries

---

## SLIDE 25: Common Pitfall #2 - Inefficient SQL Generation

### Common Pitfall #2: Inefficient SQL Generation
**When Hibernate Generates Suboptimal Queries**

**The Problem:**
Complex JPQL can generate inefficient SQL

**Example (from your code):**
Get customer loan statistics (count + total amount)

**❌ Inefficient JPQL (Correlated Subqueries)**
```java
@Query("""
    SELECT c.id, c.name, c.pan,
        (SELECT COUNT(la) FROM LoanApplication la 
         WHERE la.customerId = c.id),
        (SELECT COALESCE(SUM(la.requestedAmount), 0) 
         FROM LoanApplication la WHERE la.customerId = c.id)
    FROM Customer c
""")
```

**Generated SQL:**
```sql
SELECT c.id, c.name,
    (SELECT COUNT(*) FROM loan_application WHERE customer_id = c.id),
    (SELECT SUM(requested_amount) FROM loan_application WHERE customer_id = c.id)
FROM customer c
```
- ❌ Each subquery runs for EVERY customer
- ❌ O(N) subquery executions

---

## SLIDE 26: Inefficient SQL - Solution

### Inefficient SQL - Solution

**✅ Efficient Native SQL (JOIN + GROUP BY)**
```java
@Query(value = """
    SELECT 
        c.id,
        c.name,
        c.pan,
        COUNT(la.id) as loan_count,
        COALESCE(SUM(la.requested_amount), 0) as total
    FROM customer c
    LEFT JOIN loan_application la ON c.id = la.customer_id
    GROUP BY c.id, c.name, c.pan
""", nativeQuery = true)
```

**Benefits:**
- ✅ Single query execution
- ✅ Database-optimized JOIN
- ✅ No correlated subqueries
- ✅ O(1) query complexity

**When to Use Native SQL:**
- Complex aggregations
- Window functions (ROW_NUMBER, RANK)
- Recursive queries
- Database-specific optimizations
- Performance-critical queries

**Best Practice:**
Use JPQL for simple queries, Native SQL for complex ones

---

## SLIDE 27: Best Practices & Summary

### Best Practices & Summary

**Development Journey:**
```
JDBC → JPA → Spring Data JPA → Optimize
```

**Best Practices:**

✅ **Use Spring Data JPA** for most cases  
✅ **Always use @Transactional** (enables L1 cache)  
✅ **Use FetchType.LAZY** by default  
✅ **Watch for N+1** - use JOIN FETCH  
✅ **Enable SQL logging** in development  
✅ **Use native SQL** for complex queries  
✅ **Enable L2 cache** for read-heavy entities  
✅ **Profile queries** with Hibernate Statistics  

**Common Pitfalls to Avoid:**

❌ Missing @Transactional  
❌ FetchType.EAGER everywhere  
❌ Not handling N+1 problem  
❌ Complex JPQL without checking generated SQL  
❌ No query optimization  

**Performance Checklist:**
- Enable `hibernate.show_sql=true` in dev
- Use `hibernate.generate_statistics=true`
- Monitor query counts per request
- Profile with tools (JPA Buddy, Hibernate Stats)

---

## SLIDE 28: Your Project Structure

### Your Project Structure
**Complete Code Examples Available**

**Repository: hibernate-training**

```
src/main/java/com/mv/hibernate/
├── service/
│   ├── JDBCExample.java
│   ├── JDBCPreparedStatement.java
│   ├── JPAExample.java
│   └── SpringDataJPAExample.java
├── FirstLevelCache/
│   ├── FirstLevelCacheExample.java
│   ├── DifferentTransactionsCacheExample.java
│   ├── UpdatesInCacheExample.java
│   └── SaveAndCacheExample.java
├── SecondLevelCache/
│   └── SecondLevelCacheExample.java
├── OneToMany/
│   └── OneToManyJPAExample.java
├── ManyToOne/
│   └── ManyToOneJPAExample.java
├── ManyToMany/
│   └── ManyToManyJPAExample.java
├── NPlusOne/
│   └── NPlusOneProblemDemo.java
└── InefficientQuery/
    └── InefficientSQLExample.java
```

**All examples are runnable!**
```bash
mvn spring-boot:run
```

---

## SLIDE 29: Thank You!

# Thank You!
## Questions?

**Sagar Kanojia**

📧 Email: [your-email]  
💼 LinkedIn: [your-linkedin]  
🐙 GitHub: [your-github/hibernate-training]  

**Resources:**
- Hibernate Documentation: https://hibernate.org
- Spring Data JPA Docs: https://spring.io/projects/spring-data-jpa
- JPA Specification: https://jakarta.ee/specifications/persistence

---

## Presentation Metadata

- **Total Slides:** 29
- **Estimated Duration:** 45-60 minutes
- **Target Audience:** Java developers learning persistence
- **Prerequisites:**
  - Basic Java knowledge
  - Understanding of SQL
  - Familiarity with Maven/Spring Boot

**Tools Needed:**
- IDE (IntelliJ IDEA / Eclipse)
- MySQL/H2 database
- Maven
- Spring Boot

---

## How to Use This Document

### For PowerPoint/Google Slides:
1. Copy each slide section (SLIDE 1, SLIDE 2, etc.)
2. Paste into a new slide
3. Format code blocks with monospace font (Courier New/Consolas)
4. Add diagrams using shapes/SmartArt for architecture slides
5. Use screenshots from actual code for authenticity

### For Markdown Presentation Tools:
- **Marp**: Use as-is with minimal modifications
- **Reveal.js**: Convert horizontal rules (---) to section breaks
- **Slidev**: Add frontmatter and slide directives

### Live Demo Tips:
1. Run examples during presentation
2. Show SQL logs in console
3. Demonstrate N+1 problem with query counts
4. Ask audience to predict SQL output (interactive!)

---

**End of Presentation Document**
