package com.eworld.repository.category;

import com.eworld.contstant.CategoryStatus;
import com.eworld.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer>, CategoryCustomRepository {
	
	@Query("FROM Category c WHERE c.status LIKE :status")
	List<Category> findByInStatus( @Param(value = "status") CategoryStatus status);
	
	@Query("FROM Category c WHERE c.name LIKE %:keyword% OR c.status LIKE %:keyword%")
	Page<Category> findByKeyWord(@Param(value = "keyword") String keyword, Pageable pageable);

	@Query("UPDATE Category SET status= :status WHERE id=:categoryId")
	@Modifying
	public void changeStatus(@Param(value = "status") CategoryStatus status, @Param(value = "categoryId") Integer id);

	@Query("SELECT DISTINCT c FROM Category c INNER JOIN Product p ON p.categoryId = c.id"
	       +" WHERE p.id IN(SELECT od.productId FROM OrderDetail od INNER JOIN Order o ON o.id= od.orderId WHERE MONTH(o.createdAt)= :month)")
	public List<Category> listCategoryHotSale(@Param(value = "month") Integer month);
}
