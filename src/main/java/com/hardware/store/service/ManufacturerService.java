package com.hardware.store.service;

import com.hardware.store.dto.ManufacturerDto;

import java.util.List;

public interface ManufacturerService {
    List<ManufacturerDto>getManufacturers();
    ManufacturerDto getManufacturerById(Integer id);
    ManufacturerDto createManufacturer(ManufacturerDto manufacturerDto);
    ManufacturerDto updateManufacturer(Integer id, ManufacturerDto manufacturerDto);
    void deleteManufacturer(Integer id);

}
