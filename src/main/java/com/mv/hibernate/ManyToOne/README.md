# Many-to-One Relationship Example

This package demonstrates how to implement and fetch **Many-to-One relationships** in Java using **JPA/Hibernate**.

## 📚 Scenario

**Many Loan Applications belong to One Customer**

- **Loan Application** (MANY side) - Child entity with foreign key
- **Customer** (ONE side) - Parent entity
- Relationship: `loan_application.customer_id` → `customer.id` (foreign key)
- This is the **INVERSE** of the One-to-Many relationship

## 📁 Files Overview

### 1. Entity Class

#### `LoanApplicationWithCustomer.java` (in model package)
- Represents the **"MANY"** side of the relationship
- Contains `@ManyToOne` annotation mapping to Customer
- Uses **Eager Loading** - customer loaded automatically

```java
@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "customer_id", referencedColumnName = "id")
private Customer customer;
```

**Key Points:**
- `@ManyToOne` defines the relationship
- `@JoinColumn` specifies the foreign key column
- `FetchType.EAGER` loads customer immediately
- This is the **"owning"** side of the relationship (has the foreign key)

### 2. Repository

#### `LoanApplicationWithCustomerRepository.java`
- Spring Data JPA repository
- Provides automatic CRUD operations
- Custom methods:
  - `findByCustomerId(Long customerId)` - Get all loans for a customer
  - `findByProductType(String productType)` - Get loans by type
  - `findByStatus(String status)` - Get loans by status

### 3. Service Example

#### `ManyToOneJPAExample.java` ⭐
**JPA/Hibernate Approach** - Shows many-to-one with automatic customer loading

**Methods:**
1. `getLoanApplicationWithCustomer(Long loanApplicationId)` - Get one loan with customer
2. `getLoanApplicationsByCustomerId(Long customerId)` - Get all loans for one customer
3. `getLoanApplicationsByProductType(String productType)` - Get loans by type
4. `demonstrateManyToOneMapping()` - Run complete demo

**Example:**
```java
// Fetch loan application
LoanApplicationWithCustomer loanApp = repository.findById(1L).get();

// Customer is automatically loaded!
Customer customer = loanApp.getCustomer();
System.out.println("Customer: " + customer.getName());
```

## 🚀 How to Run

The demo runs automatically when you start the application:

```bash
mvn spring-boot:run
```

## 📊 What Gets Demonstrated

The demo shows:
1. **Loan Application ID 1** - PERSONAL_LOAN with customer Rohit Sharma
2. **All Loan Applications for Customer 1** - Shows all 3 applications
3. **All HOME_LOAN applications** - Filtered by product type

### Example Output:

```
╔═══════════════════════════════════════════════════════╗
║   MANY-TO-ONE MAPPING DEMONSTRATION (JPA)           ║
╚═══════════════════════════════════════════════════════╝

========================================
JPA Example: Fetching Loan Application with Customer
========================================
Loan Application Found: ID=1, Type=PERSONAL_LOAN, Amount=500000.00

Customer Details:
  - ID: 1
  - Name: Rohit Sharma
  - PAN: ABCDE1234F
  - Risk Score: 720

Loan Application Details:
  - Product Type: PERSONAL_LOAN
  - Requested Amount: ₹500000.00
  - Tenure: 36 months
  - Status: APPROVED
========================================

JPA Example: Fetching All Loan Applications for Customer ID: 1
Found 3 loan application(s) for Customer ID: 1

Customer: Rohit Sharma
Loan Applications:
----------------------------------------
  - ID: 1, Type: PERSONAL_LOAN, Amount: ₹500000.00, Status: APPROVED
  - ID: 26, Type: BUSINESS_LOAN, Amount: ₹750000.00, Status: APPROVED
  - ID: 27, Type: HOME_LOAN, Amount: ₹3500000.00, Status: PENDING
```

## 🎓 Key Learning Points

### Many-to-One vs One-to-Many

#### Many-to-One (This Example):
```java
// In LoanApplication entity
@ManyToOne
@JoinColumn(name = "customer_id")
private Customer customer;

// Usage
LoanApplication loan = loanRepo.findById(1L).get();
Customer customer = loan.getCustomer();  // Get the ONE customer
```

#### One-to-Many (Opposite Direction):
```java
// In Customer entity
@OneToMany
@JoinColumn(name = "customer_id")
private List<LoanApplication> loanApplications;

// Usage
Customer customer = customerRepo.findById(1L).get();
List<LoanApplication> loans = customer.getLoanApplications();  // Get MANY loans
```

### The "Owning" Side

- **Many-to-One side (LoanApplication)** = **Owning side**
  - Has the foreign key column (`customer_id`)
  - Controls the relationship
  - Should use `@JoinColumn`

- **One-to-Many side (Customer)** = **Non-owning side**
  - Doesn't have the foreign key
  - Uses `mappedBy` (if bidirectional)

### Why Use Many-to-One?

✅ **Natural perspective** - "A loan belongs to a customer"
✅ **Efficient queries** - Join happens naturally when fetching loans
✅ **Common use case** - Most queries fetch child with parent info
✅ **Simpler SQL** - Direct foreign key relationship

### When to Use What?

| Use Case | Use This |
|----------|----------|
| Get loan with customer details | Many-to-One |
| Get customer with all their loans | One-to-Many |
| List all loans showing customer names | Many-to-One |
| Show customer profile with loan history | One-to-Many |

## 🔍 Database Schema

```sql
-- Customer table (ONE side - referenced)
CREATE TABLE customer (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    pan VARCHAR(20) UNIQUE,
    dob DATE,
    risk_score INT
);

-- Loan Application table (MANY side - owns the foreign key)
CREATE TABLE loan_application (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    customer_id BIGINT NOT NULL,        -- Foreign Key (Many → One)
    product_type VARCHAR(50),
    requested_amount DECIMAL(15,2),
    tenure_months INT,
    status VARCHAR(30),
    created_at TIMESTAMP,
    CONSTRAINT fk_la_customer
        FOREIGN KEY (customer_id) REFERENCES customer(id)
);
```

## 💡 JPA Advantages

✅ **Automatic Joins** - Hibernate generates SQL joins automatically
✅ **No Manual Mapping** - Customer object populated automatically
✅ **Type Safe** - Compile-time checking
✅ **Clean Code** - Just call `loanApp.getCustomer()`
✅ **Lazy/Eager Options** - Control when data is loaded

## 📝 SQL Generated by Hibernate

When you fetch a loan application with customer:

```sql
SELECT 
    la.id, la.customer_id, la.product_type, 
    la.requested_amount, la.tenure_months, 
    la.status, la.created_at,
    c.id, c.name, c.pan, c.dob, c.risk_score
FROM loan_application la
LEFT JOIN customer c ON la.customer_id = c.id
WHERE la.id = ?
```

Hibernate automatically:
- Joins to customer table
- Maps both entities
- Handles null customers gracefully

## 🎯 Teaching Tips

1. **Start with the relationship** - Show the foreign key in database first
2. **Compare with One-to-Many** - Show they're two sides of same relationship
3. **Show the SQL** - Enable `show_sql=true` to see the join
4. **Demonstrate eager vs lazy** - Change `FetchType` and observe
5. **Real-world examples** - Order→Customer, Comment→Post, etc.

## 🔗 Related Concepts

- **Bidirectional Relationships** - Adding `@OneToMany` on Customer side
- **Lazy Loading** - Change to `FetchType.LAZY` for on-demand loading
- **Cascade Operations** - Automatically save/delete related entities
- **Fetch Joins** - Use JPQL to optimize loading

## 📚 Next Steps

After understanding this example, explore:
1. **Bidirectional mapping** - Add `@OneToMany` on Customer side
2. **Lazy loading with transactions** - See how `@Transactional` helps
3. **Custom queries** - Use `@Query` for complex filtering
4. **DTO projections** - Fetch only needed fields for performance

---

**Created for teaching Many-to-One relationships in JPA**
