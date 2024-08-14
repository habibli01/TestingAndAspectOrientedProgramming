package org.example.sellingexchangeplatform.mapper;

import org.example.sellingexchangeplatform.dto.response.OrderResponseDTO;
import org.example.sellingexchangeplatform.entity.Order;
import org.example.sellingexchangeplatform.Enum.OrderType; // Correct import statement
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderMapperTest {

    private OrderMapper orderMapper;

    @BeforeEach
    public void setUp() {
        // Initialize the mapper
        orderMapper = Mappers.getMapper(OrderMapper.class);
    }

    @Test
    public void testToDto() {
        // Given: a sample Order entity
        Order order = new Order();
        order.setOrderType(OrderType.SALE); // Use the enum value defined in OrderType
        // Add other fields if necessary

        // When: the order is mapped to a DTO
        OrderResponseDTO orderResponseDTO = orderMapper.toDto(order);

        // Then: verify that the fields are mapped correctly
        assertThat(orderResponseDTO).isNotNull();
        assertThat(orderResponseDTO.getOrderType()).isEqualTo(OrderType.SALE);
        // Add other assertions for remaining fields if necessary
    }
}
