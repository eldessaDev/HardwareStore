package com.hardware.store.controller;

import com.hardware.store.dto.ManufacturerDto;
import com.hardware.store.service.ManufacturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/manufacturers")
public class ManufacturerController {

    private final ManufacturerService manufacturerService;

    @GetMapping
    public ResponseEntity<List<ManufacturerDto>> getAllManufacturers() {
        return ResponseEntity.ok(manufacturerService.getManufacturers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManufacturerDto> getManufacturerById(@PathVariable Integer id) {
        return ResponseEntity.ok(manufacturerService.getManufacturerById(id));
    }

    @PostMapping
    public ResponseEntity<ManufacturerDto> createManufacturer(@RequestBody ManufacturerDto manufacturerDto) {
        return ResponseEntity.ok(manufacturerService.createManufacturer(manufacturerDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ManufacturerDto> updateManufacturer(@PathVariable Integer id, @RequestBody ManufacturerDto manufacturerDto) {
        return ResponseEntity.ok(manufacturerService.updateManufacturer(id, manufacturerDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ManufacturerDto> deleteManufacturer(@PathVariable Integer id) {
       manufacturerService.deleteManufacturer(id);
       return ResponseEntity.ok().build();
    }

}



