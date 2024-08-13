package org.example.sellingexchangeplatform.mapper;

import org.example.sellingexchangeplatform.dto.response.OrderResponseDTO;
import org.example.sellingexchangeplatform.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(source = "orderType", target = "orderType")
    OrderResponseDTO toDto(Order order);
}
