package com.mv.hibernate.OneToMany;

import com.mv.hibernate.model.CustomerWithLoans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for CustomerWithLoans entity
 * Spring Data JPA automatically provides CRUD operations
 */
@Repository
public interface CustomerWithLoansRepository extends JpaRepository<CustomerWithLoans, Long> {
    
    /**
     * Find customer by PAN
     * Spring Data JPA automatically generates the query from method name
     */
    CustomerWithLoans findByPan(String pan);
    
    /**
     * Solution for N+1 problem: Use JOIN FETCH
     * This fetches customers AND their loanApplications in a SINGLE query
     * 
     * DISTINCT is needed because JOIN can create duplicate rows
     */
    @Query("SELECT DISTINCT c FROM CustomerWithLoans c LEFT JOIN FETCH c.loanApplications")
    List<CustomerWithLoans> findAllWithLoans();
}
