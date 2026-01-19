-- Customers (40+ customers, all born after 1985, so ≤40 years old)
INSERT INTO customer (name, pan, dob, risk_score) VALUES
('Rohit Sharma', 'ABCDE1234F', '1990-05-10', 720),
('Ananya Verma', 'PQRSX5678K', '1995-08-21', 640),
('Vikram Singh', 'LMNOP2345G', '1988-03-15', 680),
('Priya Patel', 'WXYZA3456H', '1992-11-22', 750),
('Amit Kumar', 'BCDEF4567I', '1989-07-08', 690),
('Sneha Reddy', 'GHIJK5678J', '1994-01-30', 710),
('Rahul Gupta', 'MNOPQ6789K', '1991-09-12', 730),
('Kavita Jain', 'RSTUV7890L', '1987-12-05', 670),
('Arjun Nair', 'WXYZB8901M', '1993-04-18', 695),
('Meera Iyer', 'CDEFG9012N', '1986-06-25', 660),
('Sandeep Malhotra', 'HIJKL0123O', '1990-02-14', 720),
('Divya Kapoor', 'MNOPQ1234P', '1995-10-03', 740),
('Rajesh Bhatt', 'RSTUV2345Q', '1988-08-27', 685),
('Swati Desai', 'WXYZB3456R', '1992-05-09', 755),
('Karan Joshi', 'CDEFG4567S', '1989-11-16', 700),
('Nisha Agarwal', 'HIJKL5678T', '1994-07-21', 725),
('Vivek Saxena', 'MNOPQ6789U', '1991-03-04', 680),
('Poonam Yadav', 'RSTUV7890V', '1987-01-28', 645),
('Suresh Pillai', 'WXYZB8901W', '1993-12-11', 710),
('Anjali Sharma', 'CDEFG9012X', '1986-09-07', 630),
('Manoj Tiwari', 'HIJKL0123Y', '1990-06-19', 715),
('Rashmi Bose', 'MNOPQ1234Z', '1995-04-02', 735),
('Ashok Rana', 'RSTUV2345A', '1988-10-23', 690),
('Kiran Mehta', 'WXYZB3456B', '1992-02-08', 760),
('Deepak Chauhan', 'CDEFG4567C', '1989-08-31', 705),
('Sunita Roy', 'HIJKL5678D', '1994-12-15', 720),
('Ganesh Prasad', 'MNOPQ6789E', '1991-05-26', 675),
('Rekha Das', 'RSTUV7890F', '1987-07-13', 650),
('Prakash Singh', 'WXYZB8901G', '1993-09-29', 705),
('Maya Banerjee', 'CDEFG9012H', '1986-11-04', 635),
('Vinod Khanna', 'HIJKL0123I', '1990-01-17', 710),
('Alka Gupta', 'MNOPQ1234J', '1995-03-22', 730),
('Ravi Shankar', 'RSTUV2345K', '1988-12-09', 695),
('Lata Mishra', 'WXYZB3456L', '1992-06-14', 765),
('Ajay Mathur', 'CDEFG4567M', '1989-04-27', 700),
('Bina Choudhury', 'HIJKL5678N', '1994-08-10', 725),
('Sanjay Rao', 'MNOPQ6789O', '1991-10-31', 680),
('Chitra Venkatesh', 'RSTUV7890P', '1987-02-24', 655),
('Dinesh Kumar', 'WXYZB8901Q', '1993-11-18', 700),
('Farzana Sheikh', 'CDEFG9012R', '1986-04-12', 640),
('Girish Jain', 'HIJKL0123S', '1990-07-05', 715),
('Hema Krishnan', 'MNOPQ1234T', '1995-09-16', 740),
('Inderpal Singh', 'RSTUV2345U', '1988-01-03', 690),
('Jaya Lakshmi', 'WXYZB3456V', '1992-03-19', 770);

-- Loan Applications (25 applications)
INSERT INTO loan_application (customer_id, product_type, requested_amount, tenure_months, status, created_at) VALUES
(1, 'PERSONAL_LOAN', 500000.00, 36, 'APPROVED', CURRENT_TIMESTAMP),
(2, 'PERSONAL_LOAN', 300000.00, 24, 'APPROVED', CURRENT_TIMESTAMP),
(3, 'HOME_LOAN', 2500000.00, 240, 'APPROVED', CURRENT_TIMESTAMP),
(4, 'PERSONAL_LOAN', 400000.00, 48, 'APPROVED', CURRENT_TIMESTAMP),
(5, 'BUSINESS_LOAN', 1000000.00, 60, 'PENDING', CURRENT_TIMESTAMP),
(6, 'PERSONAL_LOAN', 600000.00, 36, 'APPROVED', CURRENT_TIMESTAMP),
(7, 'EDUCATION_LOAN', 800000.00, 72, 'APPROVED', CURRENT_TIMESTAMP),
(8, 'PERSONAL_LOAN', 350000.00, 24, 'REJECTED', CURRENT_TIMESTAMP),
(9, 'HOME_LOAN', 3000000.00, 240, 'APPROVED', CURRENT_TIMESTAMP),
(10, 'PERSONAL_LOAN', 450000.00, 36, 'APPROVED', CURRENT_TIMESTAMP),
(11, 'BUSINESS_LOAN', 1500000.00, 84, 'APPROVED', CURRENT_TIMESTAMP),
(12, 'PERSONAL_LOAN', 250000.00, 24, 'APPROVED', CURRENT_TIMESTAMP),
(13, 'HOME_LOAN', 1800000.00, 180, 'APPROVED', CURRENT_TIMESTAMP),
(14, 'PERSONAL_LOAN', 550000.00, 48, 'APPROVED', CURRENT_TIMESTAMP),
(15, 'EDUCATION_LOAN', 600000.00, 60, 'PENDING', CURRENT_TIMESTAMP),
(16, 'PERSONAL_LOAN', 320000.00, 36, 'APPROVED', CURRENT_TIMESTAMP),
(17, 'BUSINESS_LOAN', 800000.00, 48, 'APPROVED', CURRENT_TIMESTAMP),
(18, 'PERSONAL_LOAN', 280000.00, 24, 'REJECTED', CURRENT_TIMESTAMP),
(19, 'HOME_LOAN', 2200000.00, 216, 'APPROVED', CURRENT_TIMESTAMP),
(20, 'PERSONAL_LOAN', 480000.00, 42, 'APPROVED', CURRENT_TIMESTAMP),
(21, 'BUSINESS_LOAN', 1200000.00, 72, 'APPROVED', CURRENT_TIMESTAMP),
(22, 'PERSONAL_LOAN', 380000.00, 36, 'APPROVED', CURRENT_TIMESTAMP),
(23, 'HOME_LOAN', 2800000.00, 228, 'PENDING', CURRENT_TIMESTAMP),
(24, 'PERSONAL_LOAN', 420000.00, 48, 'APPROVED', CURRENT_TIMESTAMP),
(25, 'EDUCATION_LOAN', 700000.00, 66, 'APPROVED', CURRENT_TIMESTAMP),
-- Additional loan applications for Customer 1 (Rohit Sharma) to demonstrate One-to-Many
(1, 'BUSINESS_LOAN', 750000.00, 60, 'APPROVED', CURRENT_TIMESTAMP),
(1, 'HOME_LOAN', 3500000.00, 240, 'PENDING', CURRENT_TIMESTAMP);

-- Loans (20 active loans from approved applications)
INSERT INTO loan (loan_application_id, approved_amount, interest_rate, status) VALUES
(1, 450000.00, 12.5, 'ACTIVE'),
(2, 250000.00, 14.0, 'ACTIVE'),
(3, 2400000.00, 8.5, 'ACTIVE'),
(4, 380000.00, 13.2, 'ACTIVE'),
(6, 550000.00, 11.8, 'ACTIVE'),
(7, 750000.00, 9.5, 'ACTIVE'),
(9, 2850000.00, 8.2, 'ACTIVE'),
(10, 420000.00, 12.9, 'ACTIVE'),
(11, 1400000.00, 10.5, 'ACTIVE'),
(12, 230000.00, 14.5, 'ACTIVE'),
(13, 1700000.00, 8.8, 'ACTIVE'),
(14, 510000.00, 12.1, 'ACTIVE'),
(16, 295000.00, 13.5, 'ACTIVE'),
(17, 720000.00, 11.2, 'ACTIVE'),
(19, 2050000.00, 8.9, 'ACTIVE'),
(20, 450000.00, 12.3, 'ACTIVE'),
(21, 1100000.00, 10.8, 'ACTIVE'),
(22, 350000.00, 13.8, 'ACTIVE'),
(24, 390000.00, 12.6, 'ACTIVE'),
(25, 650000.00, 9.8, 'ACTIVE');

-- Repayment Schedules (extensive EMI schedules for all loans)
-- Repayment schedules mapped to actual loan IDs (1-20)
INSERT INTO repayment_schedule (loan_id, installment_no, due_date, principal, interest, status) VALUES
-- Loan 1 (36 months)
(1, 1, '2025-01-10', 12000.00, 3500.00, 'PAID'),
(1, 2, '2025-02-10', 12000.00, 3400.00, 'PAID'),
(1, 3, '2025-03-10', 12000.00, 3300.00, 'DUE'),
(1, 4, '2025-04-10', 12000.00, 3200.00, 'DUE'),
(1, 5, '2025-05-10', 12000.00, 3100.00, 'DUE'),
-- Loan 2 (24 months)
(2, 1, '2025-01-15', 10000.00, 3000.00, 'MISSED'),
(2, 2, '2025-02-15', 10000.00, 2900.00, 'DUE'),
(2, 3, '2025-03-15', 10000.00, 2800.00, 'DUE'),
(2, 4, '2025-04-15', 10000.00, 2700.00, 'DUE'),
-- Loan 3 (240 months home loan)
(3, 1, '2025-01-01', 8500.00, 17000.00, 'PAID'),
(3, 2, '2025-02-01', 8500.00, 16980.00, 'PAID'),
(3, 3, '2025-03-01', 8500.00, 16960.00, 'DUE'),
-- Loan 4 (48 months)
(4, 1, '2025-01-05', 7800.00, 2500.00, 'PAID'),
(4, 2, '2025-02-05', 7800.00, 2480.00, 'MISSED'),
(4, 3, '2025-03-05', 7800.00, 2460.00, 'DUE'),
-- Loan 5 (36 months) - loan_application_id 6
(5, 1, '2025-01-12', 14500.00, 4200.00, 'PAID'),
(5, 2, '2025-02-12', 14500.00, 4150.00, 'PAID'),
(5, 3, '2025-03-12', 14500.00, 4100.00, 'DUE'),
-- Loan 6 (72 months education loan) - loan_application_id 7
(6, 1, '2025-01-08', 9500.00, 3200.00, 'PAID'),
(6, 2, '2025-02-08', 9500.00, 3180.00, 'DUE'),
(6, 3, '2025-03-08', 9500.00, 3160.00, 'DUE'),
-- Loan 7 (240 months home loan) - loan_application_id 9
(7, 1, '2025-01-03', 11000.00, 19000.00, 'PAID'),
(7, 2, '2025-02-03', 11000.00, 18980.00, 'MISSED'),
(7, 3, '2025-03-03', 11000.00, 18960.00, 'DUE'),
-- Loan 8 (36 months) - loan_application_id 10
(8, 1, '2025-01-18', 11000.00, 3200.00, 'PAID'),
(8, 2, '2025-02-18', 11000.00, 3180.00, 'PAID'),
(8, 3, '2025-03-18', 11000.00, 3160.00, 'DUE'),
-- Loan 9 (84 months business loan) - loan_application_id 11
(9, 1, '2025-01-20', 15800.00, 3800.00, 'PAID'),
(9, 2, '2025-02-20', 15800.00, 3780.00, 'DUE'),
(9, 3, '2025-03-20', 15800.00, 3760.00, 'DUE'),
-- Loan 10 (24 months) - loan_application_id 12
(10, 1, '2025-01-22', 9500.00, 1800.00, 'MISSED'),
(10, 2, '2025-02-22', 9500.00, 1780.00, 'DUE'),
-- Loan 11 (180 months home loan) - loan_application_id 13
(11, 1, '2025-01-25', 8800.00, 9500.00, 'PAID'),
(11, 2, '2025-02-25', 8800.00, 9480.00, 'PAID'),
(11, 3, '2025-03-25', 8800.00, 9460.00, 'DUE'),
-- Loan 12 (48 months) - loan_application_id 14
(12, 1, '2025-01-28', 10200.00, 2900.00, 'PAID'),
(12, 2, '2025-02-28', 10200.00, 2880.00, 'MISSED'),
(12, 3, '2025-03-28', 10200.00, 2860.00, 'DUE'),
-- Loan 13 (36 months) - loan_application_id 16
(13, 1, '2025-01-30', 7800.00, 2250.00, 'PAID'),
(13, 2, '2025-02-28', 7800.00, 2230.00, 'PAID'),
(13, 3, '2025-03-30', 7800.00, 2210.00, 'DUE'),
-- Loan 14 (48 months business loan) - loan_application_id 17
(14, 1, '2025-02-01', 14200.00, 3200.00, 'PAID'),
(14, 2, '2025-03-01', 14200.00, 3180.00, 'DUE'),
-- Loan 15 (216 months home loan) - loan_application_id 19
(15, 1, '2025-02-05', 8900.00, 12000.00, 'PAID'),
(15, 2, '2025-03-05', 8900.00, 11980.00, 'MISSED'),
-- Loan 16 (42 months) - loan_application_id 20
(16, 1, '2025-02-08', 10200.00, 3200.00, 'PAID'),
(16, 2, '2025-03-08', 10200.00, 3180.00, 'DUE'),
-- Loan 17 (72 months business loan) - loan_application_id 21
(17, 1, '2025-02-10', 14200.00, 3800.00, 'PAID'),
(17, 2, '2025-03-10', 14200.00, 3780.00, 'DUE'),
-- Loan 18 (36 months) - loan_application_id 22
(18, 1, '2025-02-12', 9200.00, 2700.00, 'MISSED'),
(18, 2, '2025-03-12', 9200.00, 2680.00, 'DUE'),
-- Loan 19 (48 months) - loan_application_id 24
(19, 1, '2025-02-15', 7800.00, 2200.00, 'PAID'),
(19, 2, '2025-03-15', 7800.00, 2180.00, 'DUE'),
-- Loan 20 (66 months education loan) - loan_application_id 25
(20, 1, '2025-02-18', 9500.00, 2800.00, 'PAID'),
(20, 2, '2025-03-18', 9500.00, 2780.00, 'DUE');

-- Payments (multiple payments per loan) - corrected loan_id references
INSERT INTO payment (loan_id, amount, payment_date, mode, status) VALUES
(1, 15500.00, '2025-01-10', 'UPI', 'SUCCESS'),
(1, 15400.00, '2025-02-08', 'NET_BANKING', 'SUCCESS'),
(2, 13000.00, '2025-01-20', 'NET_BANKING', 'FAILED'),
(2, 13000.00, '2025-01-25', 'UPI', 'SUCCESS'),
(3, 25500.00, '2025-01-01', 'CHEQUE', 'SUCCESS'),
(3, 25480.00, '2025-02-01', 'UPI', 'SUCCESS'),
(4, 10300.00, '2025-01-05', 'UPI', 'SUCCESS'),
(4, 10280.00, '2025-02-10', 'NET_BANKING', 'FAILED'),
(5, 18700.00, '2025-01-12', 'UPI', 'SUCCESS'),    -- loan_application_id 6 -> loan_id 5
(5, 18650.00, '2025-02-12', 'CHEQUE', 'SUCCESS'),  -- loan_application_id 6 -> loan_id 5
(6, 12700.00, '2025-01-08', 'NET_BANKING', 'SUCCESS'), -- loan_application_id 7 -> loan_id 6
(7, 30000.00, '2025-01-03', 'CHEQUE', 'SUCCESS'),   -- loan_application_id 9 -> loan_id 7
(8, 14200.00, '2025-01-18', 'UPI', 'SUCCESS'),      -- loan_application_id 10 -> loan_id 8
(8, 14180.00, '2025-02-18', 'NET_BANKING', 'SUCCESS'), -- loan_application_id 10 -> loan_id 8
(9, 19600.00, '2025-01-20', 'CHEQUE', 'SUCCESS'),   -- loan_application_id 11 -> loan_id 9
(11, 18300.00, '2025-01-25', 'UPI', 'SUCCESS'),     -- loan_application_id 13 -> loan_id 11
(11, 18280.00, '2025-02-25', 'NET_BANKING', 'SUCCESS'), -- loan_application_id 13 -> loan_id 11
(12, 13100.00, '2025-01-28', 'UPI', 'SUCCESS'),     -- loan_application_id 14 -> loan_id 12
(13, 10050.00, '2025-01-30', 'UPI', 'SUCCESS'),     -- loan_application_id 16 -> loan_id 13
(13, 10030.00, '2025-02-28', 'NET_BANKING', 'SUCCESS'), -- loan_application_id 16 -> loan_id 13
(14, 17400.00, '2025-02-01', 'CHEQUE', 'SUCCESS'),  -- loan_application_id 17 -> loan_id 14
(15, 20900.00, '2025-02-05', 'UPI', 'SUCCESS'),     -- loan_application_id 19 -> loan_id 15
(16, 13400.00, '2025-02-08', 'NET_BANKING', 'SUCCESS'), -- loan_application_id 20 -> loan_id 16
(17, 18000.00, '2025-02-10', 'CHEQUE', 'SUCCESS'),  -- loan_application_id 21 -> loan_id 17
(19, 10000.00, '2025-02-15', 'UPI', 'SUCCESS'),     -- loan_application_id 24 -> loan_id 19
(20, 12300.00, '2025-02-18', 'NET_BANKING', 'SUCCESS'); -- loan_application_id 25 -> loan_id 20

-- Credit Checks (for most loan applications)
INSERT INTO credit_check (loan_application_id, bureau_name, score, decision) VALUES
(1, 'CIBIL', 750, 'APPROVED'),
(1, 'EQUIFAX', 745, 'APPROVED'),
(2, 'CIBIL', 630, 'MANUAL_REVIEW'),
(3, 'CIBIL', 780, 'APPROVED'),
(3, 'EXPERIAN', 775, 'APPROVED'),
(4, 'CIBIL', 720, 'APPROVED'),
(5, 'CIBIL', 580, 'REJECTED'),
(6, 'CIBIL', 760, 'APPROVED'),
(6, 'EQUIFAX', 755, 'APPROVED'),
(7, 'CIBIL', 740, 'APPROVED'),
(8, 'CIBIL', 450, 'REJECTED'),
(9, 'CIBIL', 790, 'APPROVED'),
(9, 'EXPERIAN', 785, 'APPROVED'),
(10, 'CIBIL', 730, 'APPROVED'),
(11, 'CIBIL', 710, 'APPROVED'),
(12, 'CIBIL', 650, 'MANUAL_REVIEW'),
(13, 'CIBIL', 765, 'APPROVED'),
(14, 'CIBIL', 735, 'APPROVED'),
(15, 'CIBIL', 690, 'APPROVED'),
(16, 'CIBIL', 725, 'APPROVED'),
(17, 'CIBIL', 700, 'APPROVED'),
(18, 'CIBIL', 520, 'REJECTED'),
(19, 'CIBIL', 770, 'APPROVED'),
(20, 'CIBIL', 740, 'APPROVED'),
(21, 'CIBIL', 715, 'APPROVED'),
(22, 'CIBIL', 680, 'APPROVED'),
(23, 'CIBIL', 685, 'APPROVED'),
(24, 'CIBIL', 730, 'APPROVED'),
(25, 'CIBIL', 745, 'APPROVED');

-- Audit Logs (extensive tracking)
INSERT INTO audit_log (entity_type, entity_id, old_value, new_value, changed_at) VALUES
('LOAN_STATUS', 1, 'APPROVED', 'ACTIVE', CURRENT_TIMESTAMP),
('LOAN_STATUS', 2, 'APPROVED', 'ACTIVE', CURRENT_TIMESTAMP),
('LOAN_STATUS', 3, 'APPROVED', 'ACTIVE', CURRENT_TIMESTAMP),
('LOAN_STATUS', 4, 'APPROVED', 'ACTIVE', CURRENT_TIMESTAMP),
('LOAN_APPLICATION_STATUS', 5, 'NEW', 'PENDING', CURRENT_TIMESTAMP),
('LOAN_APPLICATION_STATUS', 8, 'NEW', 'REJECTED', CURRENT_TIMESTAMP),
('INSTALLMENT_STATUS', 4, 'DUE', 'MISSED', CURRENT_TIMESTAMP),
('INSTALLMENT_STATUS', 5, 'DUE', 'MISSED', CURRENT_TIMESTAMP),
('PAYMENT_STATUS', 2, 'PENDING', 'FAILED', CURRENT_TIMESTAMP),
('PAYMENT_STATUS', 2, 'FAILED', 'SUCCESS', CURRENT_TIMESTAMP),
('CUSTOMER_RISK_SCORE', 2, '600', '640', CURRENT_TIMESTAMP),
('LOAN_INTEREST_RATE', 6, '12.0', '11.8', CURRENT_TIMESTAMP),
('LOAN_APPROVED_AMOUNT', 3, '2500000', '2400000', CURRENT_TIMESTAMP),
('INSTALLMENT_STATUS', 7, 'DUE', 'PAID', CURRENT_TIMESTAMP),
('INSTALLMENT_STATUS', 8, 'DUE', 'MISSED', CURRENT_TIMESTAMP),
('CREDIT_DECISION', 2, 'PENDING', 'MANUAL_REVIEW', CURRENT_TIMESTAMP),
('PAYMENT_MODE', 4, 'UPI', 'NET_BANKING', CURRENT_TIMESTAMP),
('LOAN_STATUS', 12, 'ACTIVE', 'ACTIVE', CURRENT_TIMESTAMP),
('CUSTOMER_PAN', 15, 'OLD_PAN', 'NEW_PAN', CURRENT_TIMESTAMP),
('LOAN_TENURE', 7, '60', '72', CURRENT_TIMESTAMP);
