CREATE TABLE customer (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    pan VARCHAR(20) UNIQUE NOT NULL,
    dob DATE NOT NULL,
    risk_score INT NOT NULL
);

CREATE TABLE loan_application (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    customer_id BIGINT NOT NULL,
    product_type VARCHAR(50),
    requested_amount DECIMAL(15,2),
    tenure_months INT,
    status VARCHAR(30),
    created_at TIMESTAMP,
    CONSTRAINT fk_la_customer
        FOREIGN KEY (customer_id) REFERENCES customer(id)
);

CREATE TABLE loan (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    loan_application_id BIGINT UNIQUE,
    approved_amount DECIMAL(15,2),
    interest_rate DECIMAL(5,2),
    status VARCHAR(30),
    CONSTRAINT fk_loan_application
        FOREIGN KEY (loan_application_id) REFERENCES loan_application(id)
);

CREATE TABLE repayment_schedule (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    loan_id BIGINT NOT NULL,
    installment_no INT,
    due_date DATE,
    principal DECIMAL(15,2),
    interest DECIMAL(15,2),
    status VARCHAR(30),
    CONSTRAINT fk_rs_loan
        FOREIGN KEY (loan_id) REFERENCES loan(id)
);

CREATE TABLE payment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    loan_id BIGINT NOT NULL,
    amount DECIMAL(15,2),
    payment_date DATE,
    mode VARCHAR(30),
    status VARCHAR(30),
    CONSTRAINT fk_payment_loan
        FOREIGN KEY (loan_id) REFERENCES loan(id)
);

CREATE TABLE credit_check (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    loan_application_id BIGINT NOT NULL,
    bureau_name VARCHAR(50),
    score INT,
    decision VARCHAR(30),
    CONSTRAINT fk_cc_application
        FOREIGN KEY (loan_application_id) REFERENCES loan_application(id)
);

CREATE TABLE audit_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    entity_type VARCHAR(50),
    entity_id BIGINT,
    old_value TEXT,
    new_value TEXT,
    changed_at TIMESTAMP
);

-- Product table for Many-to-Many relationship
CREATE TABLE product (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50),
    description TEXT,
    base_charges DECIMAL(10,2)
);

-- Junction table for Many-to-Many relationship (Customer <-> Product)
CREATE TABLE customer_product (
    customer_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    subscribed_date DATE,
    status VARCHAR(30),
    PRIMARY KEY (customer_id, product_id),
    CONSTRAINT fk_cp_customer FOREIGN KEY (customer_id) REFERENCES customer(id),
    CONSTRAINT fk_cp_product FOREIGN KEY (product_id) REFERENCES product(id)
);
