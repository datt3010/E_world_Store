package com.eworld.repository.brand;

import com.eworld.contstant.CategoryStatus;
import com.eworld.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BrandRepository extends JpaRepository<Brand, Integer>, BrandCustomRepository {

    @Query("UPDATE Brand SET status= :status WHERE id=:brandId")
    @Modifying
    public void changeStatus(@Param(value = "status") CategoryStatus status, @Param(value = "brandId") Integer id);
}
