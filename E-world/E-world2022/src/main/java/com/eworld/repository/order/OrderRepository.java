package com.eworld.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eworld.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>, OrderCustomRepository{

}
