package org.example.sellingexchangeplatform.entity;

import org.example.sellingexchangeplatform.Enum.ProductType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductTest {

    private Product product;
    private User seller;
    private User buyer;

    @BeforeEach
    void setUp() {
        seller = mock(User.class);
        buyer = mock(User.class);

        product = Product.builder()
                .name("Test Product")
                .description("Test Description")
                .productType(ProductType.SALE)
                .price(100.0)
                .seller(seller)
                .buyer(buyer)
                .isSold(false)
                .build();
    }

    @Test
    void testOnCreateSetsCreatedDate() {
        product.onCreate();  // Ürün nesnesi üzerinde `onCreate` metodunu çağırıyoruz
        assertNotNull(product.getCreatedDate());  // Oluşturulma tarihinin set edildiğini kontrol edin
        assertTrue(product.getCreatedDate().isBefore(LocalDateTime.now()) || product.getCreatedDate().isEqual(LocalDateTime.now()));
    }

    @Test
    void testProductBuilder() {
        Product builtProduct = Product.builder()
                .name("Another Product")
                .description("Another Description")
                .productType(ProductType.EXCHANGE)
                .price(200.0)
                .exchangeProductId(456L)
                .seller(seller)
                .buyer(buyer)
                .isSold(true)
                .build();

        assertEquals("Another Product", builtProduct.getName());
        assertEquals("Another Description", builtProduct.getDescription());
        assertEquals(ProductType.EXCHANGE, builtProduct.getProductType());
        assertEquals(200.0, builtProduct.getPrice());
        assertEquals(456L, builtProduct.getExchangeProductId());
        assertEquals(seller, builtProduct.getSeller());
        assertEquals(buyer, builtProduct.getBuyer());
        assertTrue(builtProduct.getIsSold());
    }
}
