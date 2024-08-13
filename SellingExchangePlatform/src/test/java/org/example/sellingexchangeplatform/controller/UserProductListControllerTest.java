package org.example.sellingexchangeplatform.controller;

import org.example.sellingexchangeplatform.dto.response.UserProductListResponseDTO;
import org.example.sellingexchangeplatform.service.UserProductListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserProductListControllerTest {

    @InjectMocks
    private UserProductListController userProductListController;

    @Mock
    private UserProductListService userProductListService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserProductList() {
        // Arrange
        Long userId = 1L;
        List<UserProductListResponseDTO> userProductList = List.of(new UserProductListResponseDTO());
        when(userProductListService.getUserProductList(userId)).thenReturn(userProductList);

        // Act
        ResponseEntity<List<UserProductListResponseDTO>> response = userProductListController.getUserProductList(userId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(userProductList, response.getBody());
    }

    @Test
    void testAddProductToList() {
        // Arrange
        Long userId = 1L;
        Long productId = 1L;
        doNothing().when(userProductListService).addProductToList(userId, productId);

        // Act
        ResponseEntity<Void> response = userProductListController.addProductToList(userId, productId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        verify(userProductListService, times(1)).addProductToList(userId, productId);
    }

    @Test
    void testRemoveProductFromList() {
        // Arrange
        Long userId = 1L;
        Long productId = 1L;
        doNothing().when(userProductListService).removeProductFromList(userId, productId);

        // Act
        ResponseEntity<Void> response = userProductListController.removeProductFromList(userId, productId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        verify(userProductListService, times(1)).removeProductFromList(userId, productId);
    }
}
