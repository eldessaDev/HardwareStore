package com.hardware.store.mapper;

import com.hardware.store.dto.ManufacturerDto;
import com.hardware.store.entity.Manufacturer;

public class ManufacturerMapper {

    public ManufacturerDto manufacturerToDto(Manufacturer manufacturer){
        ManufacturerDto manufacturerDto = new ManufacturerDto();
        manufacturerDto.setId(manufacturer.getId());
        manufacturerDto.setName(manufacturer.getName());
        manufacturerDto.setWebsite(manufacturer.getWebsite());
        return manufacturerDto;
    }

    public Manufacturer manufacturerToEntity(ManufacturerDto manufacturerDto){
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(manufacturerDto.getId());
        manufacturer.setName(manufacturerDto.getName());
        manufacturer.setWebsite(manufacturerDto.getWebsite());
        return  manufacturer;
    }
}
