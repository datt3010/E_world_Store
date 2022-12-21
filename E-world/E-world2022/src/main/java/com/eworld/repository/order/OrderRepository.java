package com.eworld.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eworld.entity.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer>, OrderCustomRepository{

    @Query("SELECT o FROM Order o WHERE o.account.username = :username")
    List<Order> findByUserName(@Param(value = "username") String username);
}
