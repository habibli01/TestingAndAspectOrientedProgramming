package org.example.sellingexchangeplatform.controller;

import org.example.sellingexchangeplatform.dto.request.ProductRequestDTO;
import org.example.sellingexchangeplatform.dto.response.ProductResponseDTO;
import org.example.sellingexchangeplatform.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct() {
        // Arrange
        Long sellerId = 1L;
        ProductRequestDTO productRequestDTO = new ProductRequestDTO();
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        when(productService.createProduct(productRequestDTO, sellerId)).thenReturn(productResponseDTO);

        // Act
        ResponseEntity<ProductResponseDTO> response = productController.createProduct(productRequestDTO, sellerId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(productResponseDTO, response.getBody());
    }

    @Test
    void testUpdateProduct() {
        // Arrange
        Long productId = 1L;
        ProductRequestDTO productRequestDTO = new ProductRequestDTO();
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        when(productService.updateProduct(productId, productRequestDTO)).thenReturn(productResponseDTO);

        // Act
        ResponseEntity<ProductResponseDTO> response = productController.updateProduct(productId, productRequestDTO);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(productResponseDTO, response.getBody());
    }

    @Test
    void testGetUserActiveProducts() {
        // Arrange
        Long userId = 1L;
        List<ProductResponseDTO> products = List.of(new ProductResponseDTO(), new ProductResponseDTO());
        when(productService.getUserActiveProducts(userId)).thenReturn(products);

        // Act
        ResponseEntity<List<ProductResponseDTO>> response = productController.getUserActiveProducts(userId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(products, response.getBody());
    }

    @Test
    void testSellProduct() {
        // Arrange
        Long productId = 1L;
        Long buyerId = 2L;
        doNothing().when(productService).sellProduct(productId, buyerId);

        // Act
        ResponseEntity<Void> response = productController.sellProduct(productId, buyerId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        verify(productService, times(1)).sellProduct(productId, buyerId);
    }

    @Test
    void testGetProductById() {
        // Arrange
        Long id = 1L;
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        when(productService.getProductById(id)).thenReturn(productResponseDTO);

        // Act
        ResponseEntity<ProductResponseDTO> response = productController.getProductById(id);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(productResponseDTO, response.getBody());
    }

    @Test
    void testGetAllProducts() {
        // Arrange
        List<ProductResponseDTO> products = List.of(new ProductResponseDTO(), new ProductResponseDTO());
        when(productService.getAllProducts()).thenReturn(products);

        // Act
        ResponseEntity<List<ProductResponseDTO>> response = productController.getAllProducts();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(products, response.getBody());
    }
}
