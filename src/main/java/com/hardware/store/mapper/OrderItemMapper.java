package com.hardware.store.mapper;

import com.hardware.store.dto.OrderItemDto;
import com.hardware.store.entity.OrderItem;

public class OrderItemMapper {

    // 1. De la Base de Datos (Entity) -> Hacia afuera (DTO)
    public OrderItemDto toDto(OrderItem orderItem){

        // Corrección 1: Si es NULL (no existe), devolvemos null.
        if (orderItem == null){
            return null;
        }

        OrderItemDto dto = new OrderItemDto();

        // Copiamos los datos simples
        dto.setId(orderItem.getId());
        dto.setQuantity(orderItem.getQuantity());
        dto.setPriceAtPurchase(orderItem.getPriceAtPurchase());

        // Corrección 2: Las Relaciones (La parte "Tricky")
        // Preguntamos: "¿Este item tiene una Orden asociada?"
        if (orderItem.getOrder() != null){
            // Si sí, entramos a la orden y sacamos SU id.
            dto.setOrderId(orderItem.getOrder().getId());
        }

        // Preguntamos: "¿Este item tiene un Producto asociado?"
        if (orderItem.getProduct() != null){
            // Si sí, entramos al producto y sacamos SU id.
            dto.setProductId(orderItem.getProduct().getId());
        }

        return dto;
    }

    // 2. De afuera (DTO) -> Hacia la Base de Datos (Entity)
    public OrderItem toEntity(OrderItemDto dto){
        if (dto == null){
            return null;
        }

        OrderItem orderItem = new OrderItem();

        // Solo copiamos datos simples.
        // El Service se encargará de buscar la Orden y el Producto reales usando los IDs.
        orderItem.setQuantity(dto.getQuantity());
        orderItem.setPriceAtPurchase(dto.getPriceAtPurchase());

        return orderItem;
    }
}