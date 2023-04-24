package com.eworld.service.impl;

import com.eworld.dto.order.OrderDetailDto;
import com.eworld.entity.OrderDetail;
import com.eworld.projector.OrderDetailProjector;
import com.eworld.repository.order.OrderDetailRepository;
import com.eworld.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetailDto> findByOrderId(Integer id) {
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(id);
        List<OrderDetailDto> dto = OrderDetailProjector.convertToPageDto(orderDetailList);
        return dto;
    }
}
