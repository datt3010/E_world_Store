package com.eworld.service;

import com.eworld.dto.order.OrderDetailDto;

import java.util.List;

public interface OrderDetailService {
   public List<OrderDetailDto> findByOrderId(Integer id);
}
