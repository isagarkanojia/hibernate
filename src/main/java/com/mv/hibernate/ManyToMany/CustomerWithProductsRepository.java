package com.mv.hibernate.ManyToMany;

import com.mv.hibernate.model.CustomerWithProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for CustomerWithProducts entity
 * Spring Data JPA automatically provides CRUD operations
 */
@Repository
public interface CustomerWithProductsRepository extends JpaRepository<CustomerWithProducts, Long> {
    
    /**
     * Find customer by PAN
     */
    CustomerWithProducts findByPan(String pan);
    
    /**
     * Find all customers who have a specific product
     * Uses JPQL to query through the Many-to-Many relationship
     */
    @Query("SELECT c FROM CustomerWithProducts c JOIN c.products p WHERE p.id = :productId")
    List<CustomerWithProducts> findCustomersByProductId(@Param("productId") Long productId);
}
