package org.example.sellingexchangeplatform.service.impl;

import org.example.sellingexchangeplatform.Enum.OrderType;
import org.example.sellingexchangeplatform.dto.response.OrderResponseDTO;
import org.example.sellingexchangeplatform.entity.Order;
import org.example.sellingexchangeplatform.entity.Product;
import org.example.sellingexchangeplatform.entity.User;
import org.example.sellingexchangeplatform.repository.OrderRepository;
import org.example.sellingexchangeplatform.repository.ProductRepository;
import org.example.sellingexchangeplatform.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Product product;
    private User buyer;
    private User seller;  // Added seller
    private Order order;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize seller
        seller = new User();
        seller.setId(2L);
        seller.setUsername("testSeller");
        seller.setBalance(200.0);

        // Initialize product with seller
        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setPrice(100.0);
        product.setSeller(seller);  // Set the seller

        // Initialize buyer
        buyer = new User();
        buyer.setId(1L);
        buyer.setUsername("testUser");
        buyer.setBalance(150.0);

        // Initialize order
        order = new Order();
        order.setId(1L);
        order.setProduct(product);
        order.setBuyer(buyer);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderType(OrderType.SALE);
        order.setIsCompleted(true);
    }

    @Test
    void testCreateOrder_Sale() {
        // Arrange
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(buyer));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // Act
        OrderResponseDTO result = orderService.createOrder(1L, 1L, null, OrderType.SALE);

        // Assert
        assertNotNull(result);
        assertEquals(order.getId(), result.getId());
        assertEquals(product.getName(), result.getProductName());
        assertEquals(buyer.getUsername(), result.getBuyerUsername());
        verify(productRepository, times(1)).save(product);
        verify(userRepository, times(1)).save(buyer);
    }


}
