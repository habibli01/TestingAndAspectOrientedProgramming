package org.example.sellingexchangeplatform.mapper;

import org.example.sellingexchangeplatform.dto.response.ProductResponseDTO;
import org.example.sellingexchangeplatform.entity.Product;
import org.example.sellingexchangeplatform.entity.User;
import org.example.sellingexchangeplatform.Enum.ProductType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductMapperTest {

    private ProductMapper productMapper;

    @BeforeEach
    public void setUp() {
        // Initialize the mapper
        productMapper = Mappers.getMapper(ProductMapper.class);
    }

    @Test
    public void testToDto() {
        // Given: a sample Product entity
        User seller = new User();
        seller.setUsername("john_doe");

        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("This is a test product");
        product.setPrice(100.0); // Set price as Double
        product.setProductType(ProductType.SALE);
        product.setIsSold(false);
        product.setCreatedDate(LocalDateTime.now());
        product.setSeller(seller);

        // When: the product is mapped to a DTO
        ProductResponseDTO productResponseDTO = productMapper.toDto(product);

        // Then: verify that the fields are mapped correctly
        assertThat(productResponseDTO).isNotNull();
        assertThat(productResponseDTO.getId()).isEqualTo(1L);
        assertThat(productResponseDTO.getName()).isEqualTo("Test Product");
        assertThat(productResponseDTO.getDescription()).isEqualTo("This is a test product");
        assertThat(productResponseDTO.getIsSold()).isEqualTo(false);
        assertThat(productResponseDTO.getCreatedDate()).isEqualToIgnoringNanos(LocalDateTime.now());
        assertThat(productResponseDTO.getSellerName()).isEqualTo("john_doe");
    }
}
