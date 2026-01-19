# One-to-Many Relationship Implementation Guide

## 📋 Overview

This document explains the complete implementation of **One-to-Many relationship** between Customer and LoanApplication entities, demonstrating both **JPA/Hibernate** and **JDBC PreparedStatement** approaches.

## 🎯 What Was Created

### 1. **Entity Classes**

#### `/src/main/java/com/mv/hibernate/model/LoanApplication.java`
- New entity representing loan applications
- Maps to `loan_application` table
- Contains: id, customerId, productType, requestedAmount, tenureMonths, status, createdAt
- Simple POJO with JPA annotations

#### `/src/main/java/com/mv/hibernate/OneToMany/CustomerWithLoans.java`
- Customer entity with One-to-Many relationship
- Uses `@OneToMany` annotation to link to LoanApplications
- Demonstrates lazy loading with `FetchType.LAZY`
- Maps to existing `customer` table

### 2. **Repository**

#### `/src/main/java/com/mv/hibernate/OneToMany/CustomerWithLoansRepository.java`
- Spring Data JPA repository interface
- Extends `JpaRepository<CustomerWithLoans, Long>`
- Provides automatic CRUD operations
- Custom method: `findByPan(String pan)`

### 3. **Service Examples**

#### `/src/main/java/com/mv/hibernate/OneToMany/OneToManyJPAExample.java`
**JPA/Hibernate Approach** - Clean, declarative way to fetch related data

**Key Methods:**
- `getCustomerWithLoanApplications(Long customerId)` - Fetch customer by ID with all loan applications
- `getCustomerByPanWithLoanApplications(String pan)` - Fetch customer by PAN with loan applications
- `demonstrateOneToManyMapping()` - Run complete demo

**Key Features:**
```java
@Transactional(readOnly = true)  // Keeps session open for lazy loading
public void getCustomerWithLoanApplications(Long customerId) {
    CustomerWithLoans customer = customerRepository.findById(customerId).get();
    List<LoanApplication> loanApps = customer.getLoanApplications(); // Lazy loaded
    // Display data...
}
```

#### `/src/main/java/com/mv/hibernate/OneToMany/OneToManyJDBCExample.java`
**JDBC PreparedStatement Approach** - Manual SQL for full control

**Key Methods:**
- `getCustomerWithLoanApplications(Long customerId)` - Same functionality using raw SQL
- `getCustomerByPanWithLoanApplications(String pan)` - Fetch by PAN using SQL
- `demonstrateOneToManyMapping()` - Run complete demo

**Key Features:**
```java
// Step 1: Fetch customer
CustomerWithLoanData customer = fetchCustomerById(customerId);

// Step 2: Fetch loan applications separately
List<LoanApplication> loanApps = fetchLoanApplicationsByCustomerId(customerId);

// Step 3: Manually combine data
customer.setLoanApplications(loanApps);
```

#### `/src/main/java/com/mv/hibernate/OneToMany/OneToManyDemo.java`
**Main Demo Runner** - Executes both JPA and JDBC examples for comparison

**Usage:**
```java
@Autowired
private OneToManyDemo demo;

demo.runDemo();         // Run both JPA and JDBC
demo.runJPAExample();   // Run only JPA
demo.runJDBCExample();  // Run only JDBC
```

### 4. **Documentation**

#### `/src/main/java/com/mv/hibernate/OneToMany/README.md`
- Comprehensive guide to the One-to-Many implementation
- Explains all files and their purposes
- Usage instructions
- Database schema reference
- Teaching tips

### 5. **Updated DataInitializer**

#### `/src/main/java/com/mv/hibernate/config/DataInitializer.java`
- Added `OneToManyDemo` to the CommandLineRunner
- Executes automatically when application starts
- Shows both JPA and JDBC approaches side-by-side

## 🏗️ Database Schema

```sql
-- Customer (ONE side)
CREATE TABLE customer (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    pan VARCHAR(20) UNIQUE NOT NULL,
    dob DATE NOT NULL,
    risk_score INT NOT NULL
);

-- Loan Application (MANY side)
CREATE TABLE loan_application (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    customer_id BIGINT NOT NULL,           -- Foreign Key
    product_type VARCHAR(50),
    requested_amount DECIMAL(15,2),
    tenure_months INT,
    status VARCHAR(30),
    created_at TIMESTAMP,
    CONSTRAINT fk_la_customer
        FOREIGN KEY (customer_id) REFERENCES customer(id)
);
```

## 🚀 How to Run

### Method 1: Run the Application
```bash
cd /Users/sagar.kanojia/training/hibernate
mvn spring-boot:run
```

The `OneToManyDemo` will automatically run from `DataInitializer.java` (CommandLineRunner) and display:
1. JPA example output (3 customers with their loan applications)
2. JDBC example output (same 3 customers using raw SQL)
3. Comparison summary

### Method 2: Run Specific Examples

Inject the service in any component:
```java
@Autowired
private OneToManyDemo demo;

// In your method
demo.runJPAExample();    // Only JPA
demo.runJDBCExample();   // Only JDBC
demo.runDemo();          // Both
```

### Method 3: Test in a REST Controller
```java
@RestController
@RequestMapping("/api/demo")
public class DemoController {
    
    @Autowired
    private OneToManyDemo demo;
    
    @GetMapping("/one-to-many")
    public String runDemo() {
        demo.runDemo();
        return "Check console output";
    }
}
```

## 📊 Expected Output

When you run the application, you'll see:

```
╔══════════════════════════════════════════════════════════════════╗
║        ONE-TO-MANY RELATIONSHIP DEMONSTRATION                    ║
║        Customer -> Loan Applications                            ║
╚══════════════════════════════════════════════════════════════════╝

📚 Part 1: JPA/Hibernate Approach
========================================
JPA Example: Fetching Customer with Loan Applications
========================================
Customer Found: CustomerWithLoans{id=1, name='Rohit Sharma', ...}
Number of Loan Applications: 1

Loan Applications for Customer: Rohit Sharma
----------------------------------------
  - Application ID: 1, Type: PERSONAL_LOAN, Amount: 500000.00, Status: APPROVED
========================================

📚 Part 2: JDBC PreparedStatement Approach
========================================
JDBC Example: Fetching Customer with Loan Applications
========================================
Customer Found: CustomerWithLoanData{id=1, name='Rohit Sharma', ...}
Number of Loan Applications: 1

Loan Applications for Customer: Rohit Sharma
----------------------------------------
  - Application ID: 1, Type: PERSONAL_LOAN, Amount: 500000.00, Status: APPROVED
========================================
```

## 🎓 Teaching Points

### 1. **JPA Advantages**
- ✅ **Less Code**: No manual SQL or ResultSet processing
- ✅ **Automatic Relationship Management**: `@OneToMany` handles everything
- ✅ **Lazy Loading**: Related data loaded only when needed
- ✅ **Type Safety**: Compile-time checking
- ✅ **Database Independence**: Same code works with different databases

### 2. **JDBC Advantages**
- ✅ **Full Control**: Exact SQL queries you write
- ✅ **Performance**: No ORM overhead
- ✅ **Debugging**: Can see exact SQL being executed
- ✅ **Legacy Systems**: Works with any database

### 3. **Key Concepts Demonstrated**

#### One-to-Many Relationship
```java
// JPA: Declarative
@OneToMany(fetch = FetchType.LAZY)
@JoinColumn(name = "customer_id")
private List<LoanApplication> loanApplications;

// JDBC: Imperative
List<LoanApplication> loanApps = fetchLoanApplicationsByCustomerId(customerId);
customer.setLoanApplications(loanApps);
```

#### Lazy Loading
```java
// JPA: Requires @Transactional
@Transactional(readOnly = true)
public void getCustomer() {
    Customer customer = repository.findById(1L).get();
    customer.getLoanApplications(); // Loaded here
}

// JDBC: Always explicit
List<LoanApplication> loanApps = fetchLoanApplicationsByCustomerId(id);
```

#### Transaction Management
```java
// JPA: Spring manages it
@Transactional(readOnly = true)  // Opens and closes session

// JDBC: Manual connection management
try (Connection conn = dataSource.getConnection()) {
    // queries...
} // Connection closed automatically
```

## 🔍 Comparison Table

| Feature | JPA/Hibernate | JDBC PreparedStatement |
|---------|---------------|------------------------|
| **Code Lines** | ~50 lines | ~150 lines |
| **SQL Queries** | Automatic | Manual |
| **Relationship Management** | Automatic | Manual |
| **Lazy Loading** | Yes | No |
| **Type Safety** | Yes | Partial |
| **Learning Curve** | Steeper | Easier |
| **Performance** | Good | Better (for simple queries) |
| **Maintenance** | Easier | Harder |
| **Database Independence** | High | Low |

## 📝 Code Structure

```
src/main/java/com/mv/hibernate/
├── model/
│   ├── Customer.java               (Original entity)
│   └── LoanApplication.java        (NEW - Loan application entity)
├── OneToMany/
│   ├── CustomerWithLoans.java      (NEW - Customer with @OneToMany)
│   ├── CustomerWithLoansRepository.java  (NEW - Repository)
│   ├── OneToManyJPAExample.java    (NEW - JPA demo)
│   ├── OneToManyJDBCExample.java   (NEW - JDBC demo)
│   ├── OneToManyDemo.java          (NEW - Main demo runner)
│   └── README.md                    (NEW - Documentation)
└── config/
    └── Runner.java                  (UPDATED - Added demo call)
```

## 🧪 Testing Tips

1. **Verify Data**: Check that customers 1 and 3 have loan applications in the database
2. **Compare Outputs**: JPA and JDBC should show identical data
3. **Monitor SQL**: Enable SQL logging to see queries:
   ```properties
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.format_sql=true
   ```
4. **Test Lazy Loading**: Remove `@Transactional` to see LazyInitializationException
5. **Performance Testing**: Add more data and compare execution times

## 🐛 Common Issues & Solutions

### Issue 1: LazyInitializationException
**Problem**: Accessing lazy-loaded data outside transaction
**Solution**: Add `@Transactional(readOnly = true)` to the method

### Issue 2: N+1 Query Problem
**Problem**: Multiple queries for lazy-loaded collections
**Solution**: Use `@EntityGraph` or fetch join
```java
@Query("SELECT c FROM CustomerWithLoans c LEFT JOIN FETCH c.loanApplications WHERE c.id = :id")
CustomerWithLoans findByIdWithApplications(@Param("id") Long id);
```

### Issue 3: No Data Returned
**Problem**: Wrong customer ID or no loan applications
**Solution**: Check database data with:
```sql
SELECT c.id, c.name, COUNT(la.id) 
FROM customer c 
LEFT JOIN loan_application la ON c.id = la.customer_id 
GROUP BY c.id, c.name;
```

## 📚 Next Steps

After understanding this example, explore:

1. **Bidirectional Relationships**: Add `@ManyToOne` in LoanApplication
2. **Eager Loading**: Change `FetchType.LAZY` to `FetchType.EAGER`
3. **Cascade Operations**: Add `cascade = CascadeType.ALL`
4. **Many-to-Many**: Implement Customer <-> Products relationship
5. **One-to-One**: Implement Customer <-> Profile relationship

## 🔗 References

- [JPA One-to-Many Documentation](https://docs.oracle.com/javaee/7/tutorial/persistence-intro.htm)
- [Hibernate User Guide](https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html)
- [Spring Data JPA Reference](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)

---

**Created for teaching One-to-Many relationships in JPA and JDBC**
