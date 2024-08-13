package org.example.sellingexchangeplatform.controller;

import org.example.sellingexchangeplatform.dto.response.OrderResponseDTO;
import org.example.sellingexchangeplatform.Enum.OrderType;
import org.example.sellingexchangeplatform.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrder() {
        // Arrange
        Long productId = 1L;
        Long buyerId = 2L;
        Long exchangeProductId = 3L;
        OrderType orderType = OrderType.SALE;
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        when(orderService.createOrder(productId, buyerId, exchangeProductId, orderType))
                .thenReturn(orderResponseDTO);

        // Act
        ResponseEntity<OrderResponseDTO> response = orderController.createOrder(productId, buyerId, exchangeProductId, orderType);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(orderResponseDTO, response.getBody());
    }

    @Test
    void testCreateOrderWithoutExchangeProductId() {
        // Arrange
        Long productId = 1L;
        Long buyerId = 2L;
        OrderType orderType = OrderType.SALE;
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        when(orderService.createOrder(productId, buyerId, null, orderType))
                .thenReturn(orderResponseDTO);

        // Act
        ResponseEntity<OrderResponseDTO> response = orderController.createOrder(productId, buyerId, null, orderType);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(orderResponseDTO, response.getBody());
    }

    @Test
    void testGetOrderById() {
        // Arrange
        Long id = 1L;
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        when(orderService.getOrderById(id)).thenReturn(orderResponseDTO);

        // Act
        ResponseEntity<OrderResponseDTO> response = orderController.getOrderById(id);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(orderResponseDTO, response.getBody());
    }
}
