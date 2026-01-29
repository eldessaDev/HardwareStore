package com.hardware.store.service;

import com.hardware.store.dto.OrderDto;

import java.util.List;

public interface OrderService {

    List<OrderDto> getAllOrders();

    List<OrderDto>getUserOrders(Integer userId);

    OrderDto createOrder(OrderDto orderDto);
    // AGREGA ESTO:
    OrderDto getOrderById(Integer id);

}
