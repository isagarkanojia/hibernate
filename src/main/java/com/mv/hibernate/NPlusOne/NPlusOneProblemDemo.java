package com.mv.hibernate.NPlusOne;

import com.mv.hibernate.model.CustomerWithLoans;
import com.mv.hibernate.OneToMany.CustomerWithLoansRepository;
import com.mv.hibernate.model.LoanApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Demonstrates the N+1 problem and its solution.
 * 
 * N+1 Problem: Executing 1 query to fetch N entities, 
 * then N additional queries to fetch their relationships.
 */
@Service
public class NPlusOneProblemDemo {

    @Autowired
    private CustomerWithLoansRepository customerWithLoansRepository;

    /**
     * ❌ THE PROBLEM: N+1 Queries
     * 
     * This will execute:
     * - 1 query to fetch all customers
     * - N queries to fetch loans for each customer (lazy loading)
     * 
     * Total: 1 + N queries (Performance disaster!)
     * 
     * Note: @Transactional keeps the session open so we can demonstrate N+1
     */
    @Transactional
    public void demonstrateNPlusOneProblem() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("❌ N+1 PROBLEM DEMONSTRATION");
        System.out.println("=".repeat(70));
        
        System.out.println("\n⚠️  Watch the SQL queries in logs - you'll see 1 + N queries!\n");
        
        // Query 1: Fetch all customers
        System.out.println("Step 1: Fetching all customers...");
        List<CustomerWithLoans> customers = customerWithLoansRepository.findAll();
        System.out.println("✓ Fetched " + customers.size() + " customers\n");
        
        // Query 2 to N+1: Fetch loans for EACH customer
        System.out.println("Step 2: Accessing loans for each customer (triggers N queries)...");
        int queryCount = 1; // Already executed 1 query above
        
        for (CustomerWithLoans customer : customers) {
            // ⚠️ This line triggers a separate query for EACH customer!
             List<LoanApplication> l = customer.getLoanApplications();

             int loanCount = l.size();
            
            if (loanCount > 0) {
                queryCount++;
                System.out.println("  " + customer.getName() + " has " + loanCount + 
                    " loan(s) → Triggered query #" + queryCount);
            }
        }
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("🔥 PROBLEM: Total queries executed = " + queryCount);
        System.out.println("   Formula: 1 (fetch customers) + " + (queryCount - 1) + " (fetch loans) = " + queryCount);
        System.out.println("=".repeat(70) + "\n");
    }

    /**
     * ✅ THE SOLUTION: JOIN FETCH
     * 
     * This will execute:
     * - 1 single query to fetch customers AND their loans together
     * 
     * Total: 1 query (Problem solved!)
     * 
     * Note: @Transactional keeps the session open
     */
    public void demonstrateSolutionWithJoinFetch() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("✅ SOLUTION: JOIN FETCH");
        System.out.println("=".repeat(70));
        
        System.out.println("\n✅ Using JOIN FETCH - watch for a SINGLE query!\n");
        
        // Single query: Fetches customers AND loans together in ONE query
        System.out.println("Step 1: Fetching all customers WITH their loans...");
        List<CustomerWithLoans> customers = customerWithLoansRepository.findAllWithLoans();
        System.out.println("✓ Fetched " + customers.size() + " customers with their loans\n");
        
        // No additional queries when accessing loans!
        System.out.println("Step 2: Accessing loans for each customer (NO additional queries)...");
        for (CustomerWithLoans customer : customers) {
            int loanCount = customer.getLoanApplications().size();
            if (loanCount > 0) {
                System.out.println("  " + customer.getName() + " has " + loanCount + 
                    " loan(s) → No query! (already loaded)");
            }
        }
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("✅ SOLUTION: Total queries executed = 1");
        System.out.println("   All data fetched in a single JOIN query!");
        System.out.println("=".repeat(70) + "\n");
    }
}
