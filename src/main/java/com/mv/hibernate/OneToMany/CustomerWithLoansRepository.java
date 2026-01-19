package com.mv.hibernate.OneToMany;

import com.mv.hibernate.model.CustomerWithLoans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}
