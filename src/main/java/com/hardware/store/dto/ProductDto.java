package com.hardware.store.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Integer id;

    @NotBlank(message = "{product.name.mandatory}")
    private String name;

    private String description;

    @NotNull(message = "{product.price.mandatory}")
    @Positive(message = "{product.price.positive}")
    private BigDecimal price;

    @NotNull(message = "{product.stock.mandatory}")
    @Min(value = 0, message = "{product.stock.min}")
    private Integer stock;

    @NotNull(message = "{product.category.mandatory}")
    private Integer categoryId;

    @NotNull(message = "{product.manufacturer.mandatory}")
    private Integer manufacturerId;
}
