package com.hardware.store.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {

    private Integer id;

    @NotNull(message = "{order.product.mandatory}")
    @Min(value = 1, message = "{order.quantity.min}")
    private Integer quantity;

    private BigDecimal priceAtPurchase;
    private Integer orderId;

    @NotNull(message = "{order.product.mandatory}")
    private Integer productId;

}
