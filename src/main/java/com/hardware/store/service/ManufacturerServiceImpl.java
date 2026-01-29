package com.hardware.store.service;

import com.hardware.store.dto.ManufacturerDto;
import com.hardware.store.entity.Manufacturer;
import com.hardware.store.exception.ResourceNotFoundException;
import com.hardware.store.mapper.ManufacturerMapper;
import com.hardware.store.repository.ManufacturerRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Data
@Service
@RequiredArgsConstructor
public class ManufacturerServiceImpl implements ManufacturerService{

    //1. inject the repository
    private final ManufacturerRepository manufacturerRepository;

    //2. instantiate the mapper
    private final ManufacturerMapper manufacturerMapper = new ManufacturerMapper();


    @Override
    public List<ManufacturerDto> getManufacturers() {
        //find all manufacturer
        List<Manufacturer> manufacturersList = manufacturerRepository.findAll();

        //create an ArrayList to save
        List<ManufacturerDto> manufacturerDtosList = new ArrayList<>();

        for (Manufacturer manufacturer : manufacturersList) {
            ManufacturerDto manufacturerDto = manufacturerMapper.manufacturerToDto(manufacturer);
            manufacturerDtosList.add(manufacturerDto);

        }
        return manufacturerDtosList;
    }

    @Override
    public ManufacturerDto getManufacturerById(Integer id) {
        Manufacturer manufacturer = manufacturerRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Manufacturer not found"));
        return manufacturerMapper.manufacturerToDto(manufacturer);
    }

    @Override
    public ManufacturerDto createManufacturer(ManufacturerDto manufacturerDto) {
        // PASO 1: Convertir el DTO (que viene del cliente) a Entidad (para la DB)
        // Guardamos el resultado en una variable 'category'
        Manufacturer manufacturer = manufacturerMapper.manufacturerToEntity(manufacturerDto);

        // PASO 2: Guardar en la Base de Datos
        // IMPORTANTE: El repositorio nos devuelve una NUEVA instancia que YA TIENE EL ID generado.
        // La atrapamos en una variable 'savedCategory'.
        // Siempre Capturamos el objeto guardado en una variable nueva
        Manufacturer saveManufacturer  = manufacturerRepository.save(manufacturer);

        // PASO 3: Convertir la entidad guardada (con ID) a DTO para responder
        return manufacturerMapper.manufacturerToDto(saveManufacturer);
    }

    @Override
    public ManufacturerDto updateManufacturer(Integer id, ManufacturerDto manufacturerDto) {
        // 1. BUSCAR: Primero traemos la categoría vieja de la base de datos
        Manufacturer existingManufacturer = manufacturerRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Manufacturer not found"));

        //2. Capturar
        existingManufacturer.setName(manufacturerDto.getName());
        existingManufacturer.setWebsite(manufacturerDto.getWebsite());

        // 3. GUARDAR: Guardamos los cambios en la base de datos, atrapar de nuevo el objeto para evitar errores en db
        Manufacturer updatedManufacturer = manufacturerRepository.save(existingManufacturer);
        return manufacturerMapper.manufacturerToDto(updatedManufacturer);
    }

    @Override
    public void deleteManufacturer(Integer id) {
        Manufacturer manufacturer = manufacturerRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Manufacturer not found"));
        manufacturerRepository.delete(manufacturer);

    }
}
