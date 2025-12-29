package com.hardware.store.dto;

import com.hardware.store.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Integer id;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private Integer userId;
    private List<OrderItemDto> items;

}
