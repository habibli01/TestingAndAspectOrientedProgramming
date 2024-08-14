package org.example.sellingexchangeplatform.mapper;

import org.example.sellingexchangeplatform.dto.response.UserProductListResponseDTO;
import org.example.sellingexchangeplatform.entity.Product;
import org.example.sellingexchangeplatform.entity.UserProductList;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class UserProductListMapperTest {

    private final UserProductListMapper mapper = Mappers.getMapper(UserProductListMapper.class);

    @Test
    void testToDto() {
        // Arrange: Create a Product entity
        Product product = new Product();
        product.setId(100L);
        product.setName("Test Product");

        // Arrange: Create a UserProductList entity
        UserProductList userProductList = new UserProductList();
        userProductList.setId(1L);
        userProductList.setProduct(product);

        // Act: Map UserProductList entity to UserProductListResponseDTO
        UserProductListResponseDTO dto = mapper.toDto(userProductList);

        // Assert: Verify the mapping is correct
        assertNotNull(dto);
        assertEquals(userProductList.getId(), dto.getId());
        assertEquals(product.getId(), dto.getProductId());
        assertEquals(product.getName(), dto.getProductName());
    }
}
