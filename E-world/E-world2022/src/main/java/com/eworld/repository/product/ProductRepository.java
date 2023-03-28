package com.eworld.repository.product;

import com.eworld.contstant.ProductStatus;
import com.eworld.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer>, ProductCustomRepository {
    @Query("UPDATE Product SET status= :status WHERE id=:productId")
    @Modifying
    public void changeStatus(@Param(value = "status") ProductStatus status, @Param(value = "productId") Integer id);

    @Query("FROM Product p where p.categoryId = :categoryId")
    Page<Product> listProductByCategoryId(@Param(value = "categoryId") Integer categoryId, Pageable pageable);

    @Query("SELECT DISTINCT p FROM Product p INNER JOIN OrderDetail od ON od.productId = p.id"
          +" WHERE od.orderId IN(SELECT o.id FROM Order o WHERE MONTH(o.createdAt) = :month)")
    Page<Product> listProductHotSale(@Param(value = "month") Integer month, Pageable pageable);

    @Query("FROM Product p where p.name = :name")
    Product getProductByName(@Param(value = "name") String name);
}
