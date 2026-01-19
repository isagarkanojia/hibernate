# ✅ Many-to-One Relationship Implementation - COMPLETED

## 🎉 Your Many-to-One JPA Example is Ready!

A complete **Many-to-One relationship** demonstration between LoanApplication and Customer has been created using **JPA/Hibernate only** (no JDBC this time).

---

## 📦 What Was Created

### 1. **Entity Class**

#### `/src/main/java/com/mv/hibernate/model/LoanApplicationWithCustomer.java` ⭐ NEW
- LoanApplication entity with **@ManyToOne** relationship to Customer
- Uses `FetchType.EAGER` for automatic customer loading
- Maps to existing `loan_application` table

**Key Relationship:**
```java
@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "customer_id", referencedColumnName = "id")
private Customer customer;
```

### 2. **Repository**

#### `/src/main/java/com/mv/hibernate/ManyToOne/LoanApplicationWithCustomerRepository.java` ⭐ NEW
- Spring Data JPA repository
- Custom query methods:
  - `findByCustomerId(Long customerId)` - Get all loans for one customer
  - `findByProductType(String productType)` - Filter by loan type
  - `findByStatus(String status)` - Filter by status

### 3. **Service Example**

#### `/src/main/java/com/mv/hibernate/ManyToOne/ManyToOneJPAExample.java` ⭐ NEW
**JPA-Only Approach** - Shows many-to-one with automatic joins

**Methods:**
```java
getLoanApplicationWithCustomer(Long id)         // Get 1 loan with customer
getLoanApplicationsByCustomerId(Long id)        // Get all loans for 1 customer
getLoanApplicationsByProductType(String type)   // Filter by product type
demonstrateManyToOneMapping()                   // Main demo method
```

### 4. **Documentation**

#### `/src/main/java/com/mv/hibernate/ManyToOne/README.md` ⭐ NEW
- Complete guide to Many-to-One relationships
- Comparison with One-to-Many
- Teaching tips and SQL examples

### 5. **Updated Files**

#### `/src/main/java/com/mv/hibernate/config/HibernateConfig.java` ⭐ UPDATED
- Registered `LoanApplicationWithCustomer` entity

#### `/src/main/java/com/mv/hibernate/config/Runner.java` ⭐ UPDATED
- Now runs `ManyToOneJPAExample` demo

---

## 🚀 How to Run

```bash
cd /Users/sagar.kanojia/training/hibernate
mvn spring-boot:run
```

The demo runs automatically and shows:
1. **Loan Application ID 1** with customer details (Rohit Sharma)
2. **All 3 loans for Customer 1** (PERSONAL, BUSINESS, HOME)
3. **All HOME_LOAN applications** across all customers

---

## 📊 Expected Output

```
╔═══════════════════════════════════════════════════════╗
║   MANY-TO-ONE MAPPING DEMONSTRATION (JPA)           ║
╚═══════════════════════════════════════════════════════╝

========================================
JPA Example: Fetching Loan Application with Customer
========================================

Hibernate: 
    select
        la.id, la.customer_id, la.product_type,
        la.requested_amount, la.tenure_months,
        la.status, la.created_at,
        c.id, c.name, c.pan, c.dob, c.risk_score
    from
        loan_application la
    left join
        customer c on la.customer_id=c.id
    where
        la.id=?

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

========================================
JPA Example: Fetching All Loan Applications for Customer ID: 1
========================================

Hibernate:
    select
        ...
    from
        loan_application la
    left join
        customer c on la.customer_id=c.id
    where
        la.customer_id=?

Found 3 loan application(s) for Customer ID: 1

Customer: Rohit Sharma
Loan Applications:
----------------------------------------
  - ID: 1, Type: PERSONAL_LOAN, Amount: ₹500000.00, Status: APPROVED
  - ID: 26, Type: BUSINESS_LOAN, Amount: ₹750000.00, Status: APPROVED
  - ID: 27, Type: HOME_LOAN, Amount: ₹3500000.00, Status: PENDING
========================================
```

---

## 🎓 Key Concepts Demonstrated

### 1. Many-to-One Relationship
```java
// Many LoanApplications → One Customer
@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "customer_id")
private Customer customer;
```

### 2. Automatic Join
```java
// Just fetch the loan - customer comes automatically!
LoanApplicationWithCustomer loan = repository.findById(1L).get();
Customer customer = loan.getCustomer();  // Already loaded!
```

### 3. Query by Foreign Key
```java
// Find all loans for one customer
List<LoanApplicationWithCustomer> loans = 
    repository.findByCustomerId(1L);
```

---

## 📈 Relationship Comparison

### Many-to-One (THIS Example)
**Perspective:** From the child's view
**Use when:** Fetching child and need parent info
**Example:** "Get loan application and show customer name"

```java
LoanApplication loan = loanRepo.findById(1L);
String customerName = loan.getCustomer().getName();
```

### One-to-Many (Previous Example)
**Perspective:** From the parent's view
**Use when:** Fetching parent and need all children
**Example:** "Get customer and show all their loans"

```java
Customer customer = customerRepo.findById(1L);
List<LoanApplication> loans = customer.getLoanApplications();
```

---

## 🔍 Database Relationship

```
┌─────────────────┐         ┌──────────────────────┐
│    customer     │         │  loan_application    │
├─────────────────┤         ├──────────────────────┤
│ id (PK)         │◄────────│ id (PK)              │
│ name            │    │    │ customer_id (FK) ────┤
│ pan             │    │    │ product_type         │
│ dob             │    │    │ requested_amount     │
│ risk_score      │    │    │ tenure_months        │
└─────────────────┘    │    │ status               │
                       │    │ created_at           │
       ONE             │    └──────────────────────┘
                       │              MANY
                       │
                Foreign Key Relationship
```

---

## 📁 File Structure

```
hibernate/
├── src/main/java/com/mv/hibernate/
│   ├── model/
│   │   ├── Customer.java
│   │   ├── LoanApplication.java
│   │   ├── CustomerWithLoans.java
│   │   └── LoanApplicationWithCustomer.java ⭐ NEW
│   ├── ManyToOne/ ⭐ NEW PACKAGE
│   │   ├── LoanApplicationWithCustomerRepository.java
│   │   ├── ManyToOneJPAExample.java
│   │   └── README.md
│   └── config/
│       ├── HibernateConfig.java ⭐ UPDATED
│       └── Runner.java ⭐ UPDATED
└── MANY_TO_ONE_SUMMARY.md ⭐ THIS FILE
```

---

## ✅ Verification Checklist

- [x] LoanApplicationWithCustomer entity created with @ManyToOne
- [x] LoanApplicationWithCustomerRepository created
- [x] ManyToOneJPAExample service created (JPA only)
- [x] HibernateConfig updated to register new entity
- [x] Runner updated to run ManyToOne demo
- [x] README documentation created
- [x] Application compiles successfully ✅
- [x] SQL logging enabled to see joins

---

## 💡 Teaching Points

### Why Many-to-One is Important:

1. **Most Common Pattern** - Child entities almost always reference parent
2. **Natural Queries** - "Show me this order with customer info"
3. **Efficient** - Single join, immediate loading
4. **Real World** - Order→Customer, Comment→Post, Employee→Department

### Comparison with JDBC:

**JPA (What you created):**
```java
LoanApplication loan = repository.findById(1L).get();
String name = loan.getCustomer().getName();  // 2 lines, automatic join
```

**JDBC (What you DIDN'T have to write):**
```java
// Fetch loan
String sql1 = "SELECT * FROM loan_application WHERE id = ?";
LoanApplication loan = executeAndMap(sql1, 1L);

// Fetch customer separately
String sql2 = "SELECT * FROM customer WHERE id = ?";
Customer customer = executeAndMap(sql2, loan.getCustomerId());

// Manual association
loan.setCustomer(customer);
String name = customer.getName();  // ~20+ lines of code
```

---

## 🎯 What You've Learned

### Relationship Sides:

| Aspect | Many-to-One | One-to-Many |
|--------|-------------|-------------|
| **Side** | Owning (has FK) | Non-owning |
| **Entity** | LoanApplication | Customer |
| **Annotation** | `@ManyToOne` | `@OneToMany` |
| **Column** | `@JoinColumn` | `mappedBy` (if bidirectional) |
| **Perspective** | Child → Parent | Parent → Children |
| **Use Case** | Get child with parent | Get parent with children |

---

## 🚀 Ready to Use!

Your Many-to-One JPA example is complete and ready for teaching!

**Run it:**
```bash
mvn spring-boot:run
```

**See the magic:**
- Automatic SQL joins
- Clean, readable code
- No manual mapping
- Type-safe relationships

---

**Happy Teaching! 📚✨**
