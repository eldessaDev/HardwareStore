package com.hardware.store.service;

import com.hardware.store.dto.OrderDto;
import com.hardware.store.dto.OrderItemDto; // Importado
import com.hardware.store.entity.Order;
import com.hardware.store.entity.OrderItem; // Importado
import com.hardware.store.entity.OrderStatus; // Importado
import com.hardware.store.entity.Product;     // Importado
import com.hardware.store.entity.User;
import com.hardware.store.exception.ResourceNotFoundException;
import com.hardware.store.mapper.OrderMapper;
import com.hardware.store.repository.OrderRepository;
import com.hardware.store.repository.ProductRepository;
import com.hardware.store.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal; // Importado
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    private final OrderMapper orderMapper = new OrderMapper();

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDto> orderDtos = new ArrayList<>();
        for(Order order : orders){
            orderDtos.add(orderMapper.orderToDto(order));
        }
        return orderDtos;
    }

    @Override
    public List<OrderDto> getUserOrders(Integer userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        List<OrderDto> orderDtos = new ArrayList<>();
        for(Order order : orders){
            orderDtos.add(orderMapper.orderToDto(order));
        }
        return orderDtos;
    }

    @Override
    @Transactional
    public OrderDto createOrder(OrderDto orderDto) {
        // 1. Validar Usuario
        User user = userRepository.findById(orderDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // 2. Crear Orden Vacía (Usando el método privado de abajo)
        Order order = createEmptyOrder(user);

        // 3. Procesar Productos (Usando el método privado de abajo)
        processOrderItems(order, orderDto.getItems());

        // 4. Guardar
        Order savedOrder = orderRepository.save(order);
        return orderMapper.orderToDto(savedOrder);
    }

    // --- MÉTODOS PRIVADOS (AYUDANTES) ---

    private Order createEmptyOrder(User user) {
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(BigDecimal.ZERO);
        order.setItems(new ArrayList<>());
        return order;
    }

    private void processOrderItems(Order order, List<OrderItemDto> itemsDto) {
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemDto itemDto : itemsDto) {
            // Buscar producto
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            // Validar stock
            validateAndUpdateStock(product, itemDto.getQuantity());

            // Crear item
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setOrder(order);
            orderItem.setQuantity(itemDto.getQuantity());
            orderItem.setPriceAtPurchase(product.getPrice()); // Corregido a setPrice

            // Agregar a la lista
            order.getItems().add(orderItem);

            // Sumar al total
            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity()));
            total = total.add(itemTotal);
        }
        order.setTotalAmount(total);
    }

    private void validateAndUpdateStock(Product product, Integer quantity) {
        if (product.getStock() < quantity) {
            throw new RuntimeException("Sin stock suficiente para: " + product.getName());
        }
        product.setStock(product.getStock() - quantity);
        productRepository.save(product);
    }
}