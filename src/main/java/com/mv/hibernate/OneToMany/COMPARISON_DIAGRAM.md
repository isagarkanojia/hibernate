# One-to-Many: JPA vs JDBC - Side-by-Side Comparison

## 🏗️ Architecture Overview

```
┌─────────────────────────────────────────────────────────────────┐
│                       Application Layer                          │
│                                                                   │
│  ┌──────────────────────┐      ┌──────────────────────┐        │
│  │   OneToManyDemo      │      │                      │         │
│  │    (Main Runner)     │      │    Runner.java       │         │
│  │                      │◄─────┤ (CommandLineRunner)  │         │
│  └──────────────────────┘      └──────────────────────┘         │
│           │                                                      │
│           ├─────────────┬────────────────┐                      │
│           ▼             ▼                ▼                       │
│  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐              │
│  │OneToManyJPA │ │OneToManyJDBC│ │             │               │
│  │   Example   │ │   Example   │ │  Other...   │               │
│  └─────────────┘ └─────────────┘ └─────────────┘               │
└─────────────────────────────────────────────────────────────────┘
           │                    │
           │                    │
           ▼                    ▼
┌──────────────────┐  ┌──────────────────┐
│   JPA/Hibernate  │  │       JDBC       │
│   (Automatic)    │  │     (Manual)     │
└──────────────────┘  └──────────────────┘
           │                    │
           └────────┬───────────┘
                    ▼
         ┌──────────────────────┐
         │      Database        │
         │  (H2 In-Memory)      │
         └──────────────────────┘
```

## 📋 Side-by-Side Code Comparison

### Fetching Customer with Loan Applications

#### 🅰️ JPA Approach (OneToManyJPAExample.java)

```java
@Transactional(readOnly = true)
public void getCustomerWithLoanApplications(Long customerId) {
    // Step 1: Fetch customer (1 line)
    CustomerWithLoans customer = customerRepository.findById(customerId).get();
    
    // Step 2: Access loan applications (1 line - auto-loaded)
    List<LoanApplication> loanApps = customer.getLoanApplications();
    
    // Step 3: Use data
    logger.info("Customer: {}", customer.getName());
    for (LoanApplication app : loanApps) {
        logger.info("  Loan: {}", app.getProductType());
    }
}
```

**Lines of Code:** ~10 lines
**SQL Queries Generated:** 2 (automatic)
- SELECT customer WHERE id = ?
- SELECT loan_application WHERE customer_id = ?

#### 🅱️ JDBC Approach (OneToManyJDBCExample.java)

```java
public void getCustomerWithLoanApplications(Long customerId) {
    // Step 1: Fetch customer (manual SQL)
    String sql1 = "SELECT id, name, pan, dob, risk_score FROM customer WHERE id = ?";
    try (Connection conn = dataSource.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql1)) {
        ps.setLong(1, customerId);
        ResultSet rs = ps.executeQuery();
        CustomerWithLoanData customer = extractCustomer(rs);
        
        // Step 2: Fetch loan applications (separate query)
        String sql2 = "SELECT * FROM loan_application WHERE customer_id = ?";
        try (PreparedStatement ps2 = conn.prepareStatement(sql2)) {
            ps2.setLong(1, customerId);
            ResultSet rs2 = ps2.executeQuery();
            List<LoanApplication> loanApps = extractLoanApps(rs2);
            customer.setLoanApplications(loanApps);
        }
        
        // Step 3: Use data
        logger.info("Customer: {}", customer.getName());
        for (LoanApplication app : loanApps) {
            logger.info("  Loan: {}", app.getProductType());
        }
    }
}
```

**Lines of Code:** ~50+ lines (including helper methods)
**SQL Queries Executed:** 2 (manual)
- SELECT customer WHERE id = ?
- SELECT loan_application WHERE customer_id = ?

## 🔄 Data Flow Comparison

### JPA/Hibernate Flow

```
User Code
    │
    ├─► customerRepository.findById(1L)
    │       │
    │       ├─► Hibernate Session
    │       │       │
    │       │       ├─► SQL: SELECT * FROM customer WHERE id = 1
    │       │       │
    │       │       └─► Creates CustomerWithLoans object
    │       │
    │       └─► Returns CustomerWithLoans
    │
    ├─► customer.getLoanApplications()  [Triggers Lazy Load]
    │       │
    │       ├─► Hibernate Session (still open due to @Transactional)
    │       │       │
    │       │       ├─► SQL: SELECT * FROM loan_application WHERE customer_id = 1
    │       │       │
    │       │       └─► Creates List<LoanApplication>
    │       │
    │       └─► Returns List<LoanApplication>
    │
    └─► Use data
```

### JDBC Flow

```
User Code
    │
    ├─► fetchCustomerById(1L)
    │       │
    │       ├─► dataSource.getConnection()
    │       │       │
    │       │       ├─► SQL: SELECT * FROM customer WHERE id = 1
    │       │       │
    │       │       └─► ResultSet
    │       │
    │       ├─► extractCustomerFromResultSet(rs)
    │       │       │
    │       │       └─► Creates CustomerWithLoanData object
    │       │
    │       └─► Returns CustomerWithLoanData
    │
    ├─► fetchLoanApplicationsByCustomerId(1L)
    │       │
    │       ├─► dataSource.getConnection()
    │       │       │
    │       │       ├─► SQL: SELECT * FROM loan_application WHERE customer_id = 1
    │       │       │
    │       │       └─► ResultSet
    │       │
    │       ├─► Loop through ResultSet
    │       │       │
    │       │       └─► Creates List<LoanApplication>
    │       │
    │       └─► Returns List<LoanApplication>
    │
    ├─► customer.setLoanApplications(loanApps)  [Manual association]
    │
    └─► Use data
```

## 📊 Feature Comparison Matrix

| Feature | JPA/Hibernate | JDBC PreparedStatement |
|---------|---------------|------------------------|
| **Code Complexity** | ⭐⭐⭐⭐⭐ Low | ⭐⭐ High |
| **Code Lines** | ~10-20 lines | ~50-100 lines |
| **Boilerplate Code** | ⭐⭐⭐⭐⭐ Minimal | ⭐ Extensive |
| **Learning Curve** | ⭐⭐⭐ Moderate | ⭐⭐⭐⭐⭐ Easy |
| **SQL Control** | ⭐⭐ Limited | ⭐⭐⭐⭐⭐ Full |
| **Performance** | ⭐⭐⭐⭐ Good | ⭐⭐⭐⭐⭐ Excellent |
| **Lazy Loading** | ✅ Yes | ❌ No |
| **Caching** | ✅ Yes (1st & 2nd level) | ❌ No |
| **Type Safety** | ⭐⭐⭐⭐⭐ Strong | ⭐⭐⭐ Moderate |
| **Maintenance** | ⭐⭐⭐⭐⭐ Easy | ⭐⭐⭐ Harder |
| **Database Independence** | ⭐⭐⭐⭐ High | ⭐⭐ Low |
| **Debugging SQL** | ⭐⭐ Harder | ⭐⭐⭐⭐⭐ Easier |
| **Transaction Management** | ⭐⭐⭐⭐⭐ Automatic | ⭐⭐⭐ Manual |
| **Object Mapping** | ⭐⭐⭐⭐⭐ Automatic | ⭐⭐ Manual |

⭐ = Level of ease/capability (more stars = better)

## 🎯 When to Use What?

### Use JPA/Hibernate When:

✅ **Building standard CRUD applications**
- Most business applications
- Standard data access patterns

✅ **Need rapid development**
- Quick prototypes
- Time-sensitive projects

✅ **Working with complex domain models**
- Many relationships
- Rich entity behavior

✅ **Want database independence**
- Supporting multiple databases
- Migration scenarios

✅ **Team prefers ORM approach**
- Modern development practices
- Less SQL expertise

### Use JDBC When:

✅ **Performance is critical**
- High-volume transactions
- Microsecond-level optimization

✅ **Complex SQL queries needed**
- Advanced joins
- Window functions
- Database-specific features

✅ **Full SQL control required**
- Query optimization
- Specific execution plans

✅ **Working with legacy systems**
- Existing stored procedures
- Non-standard schemas

✅ **Batch operations**
- Large data imports
- Bulk updates

## 💻 Entity Comparison

### JPA Entity (CustomerWithLoans.java)

```java
@Entity
@Table(name = "customer")
public class CustomerWithLoans {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String pan;
    
    // ⭐ The Magic: One-to-Many relationship
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private List<LoanApplication> loanApplications;
    
    // Getters, setters...
}
```

**What Hibernate Does:**
1. Maps class to `customer` table
2. Recognizes `@OneToMany` relationship
3. Generates SQL to fetch loan applications when accessed
4. Manages proxy objects for lazy loading
5. Tracks entity state changes

### JDBC POJO (CustomerWithLoanData)

```java
public class CustomerWithLoanData {
    private Long id;
    private String name;
    private String pan;
    
    // Just a regular list, no magic
    private List<LoanApplication> loanApplications;
    
    // Getters, setters...
}
```

**What You Must Do:**
1. Write SQL to fetch customer
2. Write SQL to fetch loan applications
3. Execute both queries
4. Map ResultSet to objects
5. Manually associate them

## 🔍 SQL Generated/Written

### JPA (Automatic)

```sql
-- First query (when findById is called)
SELECT 
    c.id, c.name, c.pan, c.dob, c.risk_score 
FROM customer c 
WHERE c.id = ?

-- Second query (when getLoanApplications is called)
SELECT 
    la.id, la.customer_id, la.product_type, 
    la.requested_amount, la.tenure_months, 
    la.status, la.created_at
FROM loan_application la 
WHERE la.customer_id = ?
```

### JDBC (Manual)

```sql
-- You write this in fetchCustomerById()
SELECT 
    id, name, pan, dob, risk_score 
FROM customer 
WHERE id = ?

-- You write this in fetchLoanApplicationsByCustomerId()
SELECT 
    id, customer_id, product_type, 
    requested_amount, tenure_months, 
    status, created_at
FROM loan_application 
WHERE customer_id = ?
```

## 📈 Performance Characteristics

### JPA/Hibernate

```
Initial Load Time: ████░░░░░░ (Medium - Hibernate initialization)
Query Execution:   ████████░░ (Good - Optimized by Hibernate)
Memory Usage:      ██████████ (Higher - Entity caching, proxies)
Development Time:  ██░░░░░░░░ (Fast - Less code)
Maintenance Cost:  ██░░░░░░░░ (Low - Cleaner code)
```

### JDBC

```
Initial Load Time: ██░░░░░░░░ (Fast - Just JDBC driver)
Query Execution:   ██████████ (Excellent - Direct SQL)
Memory Usage:      ████░░░░░░ (Lower - Just POJOs)
Development Time:  ████████░░ (Slower - More code)
Maintenance Cost:  ████████░░ (Higher - More boilerplate)
```

## 🎓 Educational Value

### For Beginners: Start with JDBC
1. See exactly what's happening
2. Understand SQL fundamentals
3. Learn data mapping
4. Appreciate JPA later

### For Intermediate: Learn JPA
1. Understand abstraction benefits
2. Learn relationship mapping
3. Practice transaction management
4. Compare with JDBC experience

### For Teaching: Show Both
1. **Start with JDBC** → Show the manual work
2. **Then introduce JPA** → Show the simplification
3. **Compare results** → Same output, different code
4. **Discuss trade-offs** → When to use what

## 📊 Example Output Comparison

Both approaches produce **identical output**:

```
Customer Found: CustomerWithLoans{
    id=1, 
    name='Rohit Sharma', 
    pan='ABCDE1234F', 
    numberOfLoanApplications=1
}

Loan Applications for Customer: Rohit Sharma
----------------------------------------
  - Application ID: 1
    Type: PERSONAL_LOAN
    Amount: 500000.00
    Status: APPROVED
```

**Key Point:** Same data, different journey!

---

## 🎯 Summary

| Aspect | JPA | JDBC |
|--------|-----|------|
| **Philosophy** | "Trust the framework" | "I'm in control" |
| **Best For** | Business applications | Performance-critical systems |
| **Code Style** | Declarative (what) | Imperative (how) |
| **Abstraction** | High-level | Low-level |
| **Flexibility** | Less flexible | Highly flexible |

**Bottom Line:**
- 🚀 **Use JPA** for most applications (80% of cases)
- ⚡ **Use JDBC** when you need that extra control (20% of cases)
- 🎯 **Mix both** in the same application when needed!

---

**Both approaches are valid. Choose based on your needs! 🎯**
