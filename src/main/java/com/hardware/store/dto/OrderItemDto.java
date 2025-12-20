package com.hardware.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {

    private Integer id;
    private Integer quantity;
    private BigDecimal priceAtPurchase;
    private Integer orderId;
    private Integer productId;

}
