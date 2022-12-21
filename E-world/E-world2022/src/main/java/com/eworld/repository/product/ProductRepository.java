package com.eworld.repository.product;

import com.eworld.contstant.ProductStatus;
import com.eworld.dto.product.ProductDto;
import com.eworld.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Integer>, ProductCustomRepository {
    @Query("UPDATE Product SET status= :status WHERE id=:productId")
    @Modifying
    public void changeStatus(@Param(value = "status") ProductStatus status, @Param(value = "productId") Integer id);

    @Query("FROM Product p where p.categoryId = :categoryId")
    Page<Product> listProductByCategoryId(@Param(value = "categoryId") Integer categoryId, Pageable pageable);
}
