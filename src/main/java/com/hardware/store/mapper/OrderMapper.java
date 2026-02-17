package com.hardware.store.mapper;

import com.hardware.store.dto.OrderDto;
import com.hardware.store.dto.OrderItemDto;
import com.hardware.store.entity.Order;
import com.hardware.store.entity.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    // Instanciamos tu mapper anterior
    private final OrderItemMapper orderItemMapper;

    public OrderDto orderToDto(Order order){
        if (order == null){
            return null;
        }

        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setTotalAmount(order.getTotalAmount());
        orderDto.setStatus(order.getStatus());

        if (order.getUser() != null) {
            orderDto.setUserId(order.getUser().getId());
        }

        // --- AQUI ESTABA EL ERROR ---
        if (order.getItems() != null) {
            List<OrderItemDto> listDto = new ArrayList<>();

            for (OrderItem itemEntity : order.getItems()) {
                // CORREGIDO: Usamos "orderItemToDto" (el nombre que tú creaste)
                OrderItemDto itemDto = orderItemMapper.toDto(itemEntity);

                listDto.add(itemDto);
            }
            orderDto.setItems(listDto);
        }
        // -----------------------------

        return orderDto;
    }

    public Order dtoToEntity(OrderDto dto) {
        if (dto == null) {
            return null;
        }
        Order order = new Order();
        order.setTotalAmount(dto.getTotalAmount());
        order.setStatus(dto.getStatus());
        return order;
    }
}