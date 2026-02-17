package com.hardware.store.dto;

import com.hardware.store.entity.OrderStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "{order.user.mandatory}")
    private Integer userId;

    @NotEmpty(message = "{order.items.empty}")
    private List<@Valid OrderItemDto> items;

}
