package org.example.sellingexchangeplatform.entity;

import org.example.sellingexchangeplatform.Enum.OrderType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderTest {

    private Order order;
    private User buyer;
    private Product product;

    @BeforeEach
    void setUp() {
        buyer = mock(User.class);
        product = mock(Product.class);
        order = Order.builder()
                .buyer(buyer)
                .product(product)
                .exchangeProductId(123L)
                .orderType(OrderType.SALE)
                .orderDate(LocalDateTime.of(2023, 8, 13, 12, 0))
                .isCompleted(false)  // Bu alanın `false` olarak ayarlandığından emin olun
                .build();
    }

    @Test
    void testOrderCreation() {
        assertNotNull(order);
        assertEquals(buyer, order.getBuyer());
        assertEquals(product, order.getProduct());
        assertEquals(123L, order.getExchangeProductId());
        assertEquals(OrderType.SALE, order.getOrderType());
        assertEquals(LocalDateTime.of(2023, 8, 13, 12, 0), order.getOrderDate());
        assertFalse(order.getIsCompleted());
    }

    @Test
    void testOrderDefaults() {
        Order newOrder = new Order();
        assertFalse(newOrder.getIsCompleted());  // Varsayılan değerin `false` olduğunu kontrol edin
    }

    @Test
    void testOrderCompletion() {
        order.setIsCompleted(true);
        assertTrue(order.getIsCompleted());
    }

    @Test
    void testOnCreateSetsCreatedDate() {
        order.onCreate();
        assertNotNull(order.getCreatedDate());
        assertTrue(order.getCreatedDate().isBefore(LocalDateTime.now()) || order.getCreatedDate().isEqual(LocalDateTime.now()));
    }

    @Test
    void testOrderBuilder() {
        Order builtOrder = Order.builder()
                .buyer(buyer)
                .product(product)
                .exchangeProductId(456L)
                .orderType(OrderType.EXCHANGE)
                .orderDate(LocalDateTime.of(2023, 8, 14, 12, 0))
                .build();

        assertEquals(buyer, builtOrder.getBuyer());
        assertEquals(product, builtOrder.getProduct());
        assertEquals(456L, builtOrder.getExchangeProductId());
        assertEquals(OrderType.EXCHANGE, builtOrder.getOrderType());
        assertEquals(LocalDateTime.of(2023, 8, 14, 12, 0), builtOrder.getOrderDate());
        assertFalse(builtOrder.getIsCompleted());  // Varsayılan değerin `false` olduğunu kontrol edin
    }
}
