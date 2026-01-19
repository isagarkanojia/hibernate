# One-to-Many Relationship Example

This package demonstrates how to implement and fetch **One-to-Many relationships** in Java using both **JPA/Hibernate** and **JDBC**.

## 📚 Scenario

**One Customer can have Many Loan Applications**

- **Customer** (ONE side) - Parent entity
- **Loan Application** (MANY side) - Child entity
- Relationship: `customer.id` → `loan_application.customer_id` (foreign key)

## 📁 Files Overview

### 1. Entity Classes

#### `CustomerWithLoans.java`
- Represents the **"ONE"** side of the relationship
- Contains `@OneToMany` annotation mapping to LoanApplications
- Uses **Lazy Loading** (data loaded only when accessed)

```java
@OneToMany(fetch = FetchType.LAZY)
@JoinColumn(name = "customer_id")
private List<LoanApplication> loanApplications;
```

#### `LoanApplication.java` (in model package)
- Represents the **"MANY"** side of the relationship
- Simple entity with customer_id as foreign key
- No JPA relationship annotations (unidirectional mapping)

### 2. Repository

#### `CustomerWithLoansRepository.java`
- Spring Data JPA repository
- Provides automatic CRUD operations
- Custom method: `findByPan(String pan)`

### 3. Service Examples

#### `OneToManyJPAExample.java` ⭐
**JPA/Hibernate Approach** - Shows how to fetch customer with loan applications using JPA

**Key Features:**
- Uses `@Transactional` to keep session open for lazy loading
- Single repository call to fetch customer
- Automatic loading of related loan applications
- Clean, minimal code

**Methods:**
- `getCustomerWithLoanApplications(Long customerId)` - Fetch by ID
- `getCustomerByPanWithLoanApplications(String pan)` - Fetch by PAN
- `demonstrateOneToManyMapping()` - Run demo

#### `OneToManyJDBCExample.java` ⭐
**JDBC PreparedStatement Approach** - Shows the same operation using raw SQL

**Key Features:**
- Manual SQL queries for customer and loan applications
- Two separate database calls (one for customer, one for loans)
- Manual data mapping from ResultSet to objects
- More code but more control

**Methods:**
- `getCustomerWithLoanApplications(Long customerId)` - Fetch by ID
- `getCustomerByPanWithLoanApplications(String pan)` - Fetch by PAN
- `demonstrateOneToManyMapping()` - Run demo

#### `OneToManyDemo.java` 🎯
**Main Demo Class** - Runs both JPA and JDBC examples for comparison

**Usage:**
```java
@Autowired
private OneToManyDemo demo;

// Run complete demo (both JPA and JDBC)
demo.runDemo();

// Or run individually
demo.runJPAExample();
demo.runJDBCExample();
```

## 🚀 How to Run

### Option 1: Run from a Controller or Test
```java
@Autowired
private OneToManyDemo oneToManyDemo;

@GetMapping("/demo")
public void runDemo() {
    oneToManyDemo.runDemo();
}
```

### Option 2: Run from Runner class
Add to your `Runner.java`:
```java
@Autowired
private OneToManyDemo oneToManyDemo;

// In the run method
oneToManyDemo.runDemo();
```

## 📊 What Gets Demonstrated

The examples fetch:
1. **Customer ID 1** (Rohit Sharma) - has 1 loan application
2. **Customer by PAN** "ABCDE1234F" - has 1 loan application
3. **Customer ID 3** (Vikram Singh) - has 1 loan application

Each customer's details are displayed along with all their loan applications showing:
- Application ID
- Product Type (PERSONAL_LOAN, HOME_LOAN, etc.)
- Requested Amount
- Status (APPROVED, PENDING, REJECTED)

## 🎓 Key Learning Points

### JPA Approach Advantages:
✅ Less boilerplate code
✅ Automatic relationship management
✅ Lazy loading support
✅ Database independence
✅ Cleaner, more maintainable code

### JDBC Approach Advantages:
✅ Full control over SQL queries
✅ Better performance for complex queries
✅ No ORM overhead
✅ Direct database access

### JDBC Approach Disadvantages:
❌ More boilerplate code
❌ Manual relationship management
❌ No lazy loading
❌ Manual ResultSet mapping

## 🔍 Database Schema

```sql
-- Customer table (ONE side)
CREATE TABLE customer (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    pan VARCHAR(20) UNIQUE,
    dob DATE,
    risk_score INT
);

-- Loan Application table (MANY side)
CREATE TABLE loan_application (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    customer_id BIGINT NOT NULL,        -- Foreign Key
    product_type VARCHAR(50),
    requested_amount DECIMAL(15,2),
    tenure_months INT,
    status VARCHAR(30),
    created_at TIMESTAMP,
    CONSTRAINT fk_la_customer
        FOREIGN KEY (customer_id) REFERENCES customer(id)
);
```

## 💡 Tips for Teaching

1. **Start with Database Schema** - Show the tables and foreign key relationship
2. **Run JDBC Example First** - Helps understand what happens under the hood
3. **Then Show JPA Example** - Appreciate how much JPA simplifies the code
4. **Compare Side-by-Side** - Show how both achieve the same result
5. **Explain Lazy Loading** - Why `@Transactional` is needed in JPA example

## 🔗 Related Topics

- Many-to-One relationship (reverse mapping)
- Bidirectional relationships
- FetchType.EAGER vs FetchType.LAZY
- N+1 query problem
- @JoinColumn vs mappedBy
