package com.hardware.store.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerDto {

    private Integer id;

    @NotBlank(message = "{manufacturer.name.mandatory}")
    private String name;
    private String website;
}
