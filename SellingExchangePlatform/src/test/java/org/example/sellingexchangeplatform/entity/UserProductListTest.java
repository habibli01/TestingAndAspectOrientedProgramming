package org.example.sellingexchangeplatform.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserProductListTest {

    private UserProductList userProductList;
    private User user;
    private Product product;

    @BeforeEach
    void setUp() {
        user = mock(User.class);
        product = mock(Product.class);
        userProductList = UserProductList.builder()
                .user(user)
                .product(product)
                .build();
    }

    @Test
    void testUserProductListCreation() {
        assertNotNull(userProductList);
        assertEquals(user, userProductList.getUser());
        assertEquals(product, userProductList.getProduct());
    }

    @Test
    void testSetAddedDate() {
        LocalDateTime now = LocalDateTime.now();
        userProductList.setAddedDate(now);
        assertEquals(now, userProductList.getAddedDate());
    }

    @Test
    void testOnCreateSetsDates() {
        userProductList.onCreate();
        assertNotNull(userProductList.getAddedDate());
        assertNotNull(userProductList.getCreatedDate());
        assertTrue(userProductList.getAddedDate().isBefore(LocalDateTime.now()) || userProductList.getAddedDate().isEqual(LocalDateTime.now()));
        assertTrue(userProductList.getCreatedDate().isBefore(LocalDateTime.now()) || userProductList.getCreatedDate().isEqual(LocalDateTime.now()));
    }

    @Test
    void testUserProductListBuilder() {
        UserProductList builtUserProductList = UserProductList.builder()
                .user(user)
                .product(product)
                .build();

        assertEquals(user, builtUserProductList.getUser());
        assertEquals(product, builtUserProductList.getProduct());
    }
}
