package com.eworld.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eworld.entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

}