package com.eworld.repository.product;

import com.eworld.contstant.ProductStatus;
import com.eworld.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Integer>, ProductCustomRepository {
    @Query("UPDATE Product SET status= :status WHERE id=:productId")
    @Modifying
    public void changeStatus(@Param(value = "status") ProductStatus status, @Param(value = "productId") Integer id);
}
