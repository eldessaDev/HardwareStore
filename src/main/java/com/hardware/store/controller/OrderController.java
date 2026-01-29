package com.hardware.store.controller;

import com.hardware.store.dto.OrderDto;
import com.hardware.store.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus; // Importante para el status 201
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 1. Obtener todas las órdenes (Para ver tus ventas)
    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // 2. Obtener una orden por ID
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    // 3. ⚔️ CREAR LA ORDEN ⚔️
    // Usamos tu OrderDto existente como "Request"
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        // Asumimos que tu servicio recibe un OrderDto y devuelve la orden creada
        OrderDto newOrder = orderService.createOrder(orderDto);
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }
}