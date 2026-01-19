# ✅ Many-to-Many Relationship - COMPLETED

## 🎉 Simple Many-to-Many JPA Example is Ready!

A **focused, single-method** example demonstrating **Many-to-Many relationships** between Customer and Product using **JPA/Hibernate only**.

---

## 📦 What Was Created

### 1. **Database Schema** (Updated)

#### New Tables Added to `schema.sql`:
```sql
-- Product table
CREATE TABLE product (...)

-- Junction table (the key to Many-to-Many!)
CREATE TABLE customer_product (
    customer_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    PRIMARY KEY (customer_id, product_id),
    FOREIGN KEY (customer_id) REFERENCES customer(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);
```

### 2. **Sample Data** (Updated)

Added to `data.sql`:
- ✅ 7 banking products (Savings, Credit Cards, Insurance, Investments)
- ✅ Customer-Product relationships for 3 customers
- ✅ **Customer 1 (Rohit Sharma)** has **4 products**

### 3. **Entity Classes**

#### `Product.java` ⭐ NEW
- Simple product entity
- Fields: id, name, type, description, baseCharges

#### `CustomerWithProducts.java` ⭐ NEW
- Customer with **@ManyToMany** relationship
- Uses `@JoinTable` to specify junction table

**Key Annotation:**
```java
@ManyToMany(fetch = FetchType.EAGER)
@JoinTable(
    name = "customer_product",
    joinColumns = @JoinColumn(name = "customer_id"),
    inverseJoinColumns = @JoinColumn(name = "product_id")
)
private List<Product> products;
```

### 4. **Repository**

#### `CustomerWithProductsRepository.java` ⭐ NEW
- Simple Spring Data JPA repository
- Method: `findByPan(String pan)`

### 5. **Service with ONE Method**

#### `ManyToManyJPAExample.java` ⭐ NEW
**Single Method (as requested):**
```java
getCustomerWithProducts(Long customerId)
```

Fetches customer with all their products in one call.

### 6. **Documentation**

#### `ManyToMany/README.md` ⭐ NEW
- Explains Many-to-Many concept
- Shows database structure
- Compares with other relationships

### 7. **Updated Files**

#### `Runner.java` ⭐ UPDATED
Now calls the Many-to-Many demo

---

## 🚀 How to Run

```bash
mvn spring-boot:run
```

**What it does:**
- Fetches **Customer 1 (Rohit Sharma)**
- Shows his **4 products**:
  1. Premium Savings Account
  2. Titanium Credit Card
  3. Home Insurance
  4. Fixed Deposit

---

## 📊 Expected Output

```
╔═══════════════════════════════════════════════════════╗
║   MANY-TO-MANY MAPPING DEMONSTRATION (JPA)          ║
╚═══════════════════════════════════════════════════════╝

========================================
JPA Example: Fetching Customer with Products
========================================

Hibernate: 
    select
        c.id, c.name, c.pan, c.dob, c.risk_score
    from
        customer c
    where
        c.id=?

Hibernate:
    select
        p.id, p.name, p.type, p.description, p.base_charges
    from
        customer_product cp
    inner join
        product p on cp.product_id=p.id
    where
        cp.customer_id=?

Customer Details:
  - ID: 1
  - Name: Rohit Sharma
  - PAN: ABCDE1234F
  - Risk Score: 720

Number of Products: 4

Products subscribed by Customer: Rohit Sharma
----------------------------------------
  - ID: 1, Name: Premium Savings Account, Type: SAVINGS_ACCOUNT, Charges: ₹0.00
  - ID: 2, Name: Titanium Credit Card, Type: CREDIT_CARD, Charges: ₹999.00
  - ID: 3, Name: Home Insurance, Type: INSURANCE, Charges: ₹5000.00
  - ID: 6, Name: Fixed Deposit, Type: INVESTMENT, Charges: ₹0.00
========================================

✅ Many-to-Many demonstration completed!
```

---

## 🎓 Key Concept: The Junction Table

### What Makes Many-to-Many Special?

```
     Customer                Product
    ┌─────────┐            ┌─────────┐
    │ id      │            │ id      │
    │ name    │            │ name    │
    │ pan     │            │ type    │
    └─────────┘            └─────────┘
         │                      │
         │                      │
         └──────┐      ┌────────┘
                │      │
         ┌──────▼──────▼───────┐
         │ customer_product    │ ← Junction Table
         ├────────────────────┤
         │ customer_id (FK)    │
         │ product_id (FK)     │
         │ subscribed_date     │
         │ status              │
         └─────────────────────┘
```

### Why Junction Table?

- ❌ **Can't put foreign key in Customer table** - one customer has many products
- ❌ **Can't put foreign key in Product table** - one product belongs to many customers
- ✅ **Solution: Junction table** - stores all combinations!

---

## 📈 Relationship Comparison

| Feature | One-to-Many | Many-to-One | Many-to-Many |
|---------|-------------|-------------|--------------|
| **Example** | Customer → Loans | Loan → Customer | Customer ↔ Products |
| **Foreign Key** | In child table | In child table | In junction table |
| **Junction Table?** | ❌ No | ❌ No | ✅ **YES** |
| **JPA Annotation** | `@OneToMany` | `@ManyToOne` | `@ManyToMany` |
| **Additional Annotation** | `@JoinColumn` | `@JoinColumn` | `@JoinTable` |

---

## 🔍 Sample Data Visualization

### Customer 1 has 4 products:
```
Rohit Sharma (Customer 1)
  ├─ Product 1: Premium Savings Account
  ├─ Product 2: Titanium Credit Card
  ├─ Product 3: Home Insurance
  └─ Product 6: Fixed Deposit
```

### Product 1 belongs to 2 customers:
```
Premium Savings Account (Product 1)
  ├─ Customer 1: Rohit Sharma
  └─ Customer 3: Vikram Singh
```

This is **true Many-to-Many**! 🎯

---

## 📁 File Structure

```
hibernate/
├── src/main/
│   ├── java/com/mv/hibernate/
│   │   ├── model/
│   │   │   ├── Product.java ⭐ NEW
│   │   │   └── CustomerWithProducts.java ⭐ NEW
│   │   ├── ManyToMany/ ⭐ NEW PACKAGE
│   │   │   ├── CustomerWithProductsRepository.java
│   │   │   ├── ManyToManyJPAExample.java (ONE method)
│   │   │   └── README.md
│   │   └── config/
│   │       └── Runner.java ⭐ UPDATED
│   └── resources/
│       ├── schema.sql ⭐ UPDATED (added 2 tables)
│       └── data.sql ⭐ UPDATED (added sample data)
└── MANY_TO_MANY_SUMMARY.md ⭐ THIS FILE
```

---

## ✅ What You Get

### Clean, Simple Example:
- ✅ **ONE method** (as requested)
- ✅ **JPA only** (no JDBC)
- ✅ **Clear output** with customer and products
- ✅ **Real banking scenario** (customer products)
- ✅ **Documented** with README

### Demonstrates:
- ✅ `@ManyToMany` annotation
- ✅ `@JoinTable` usage
- ✅ Junction table concept
- ✅ Automatic relationship loading
- ✅ SQL joins through junction table

---

## 💡 Teaching Tips

1. **Start with the junction table** - Show why it's needed
2. **Draw the relationship** - Visual helps understanding
3. **Show the data** - Real examples in database
4. **Run the demo** - See the SQL queries
5. **Compare with One-to-Many** - Highlight the difference

### Key Question to Ask Students:
> "Why can't we just add a foreign key in the Customer table or Product table?"

**Answer:** Because one customer has MANY products AND one product belongs to MANY customers. We need a junction table to store all the combinations!

---

## 🎯 Real-World Use Cases

- **E-commerce**: User ↔ Products (wishlist/cart)
- **Social Media**: User ↔ Friends
- **Education**: Student ↔ Courses
- **Project Management**: Employee ↔ Projects
- **Security**: User ↔ Roles/Permissions

---

## 🚀 Ready to Run!

Your simple, focused Many-to-Many example is ready:

```bash
mvn spring-boot:run
```

**What you'll see:**
- Customer with 4 products
- Clean, formatted output
- SQL queries showing the joins
- Proof of Many-to-Many relationship

---

**Happy Teaching! 📚 Simple and effective! ✨**
