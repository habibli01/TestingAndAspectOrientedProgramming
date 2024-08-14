package org.example.sellingexchangeplatform.service.impl;

import org.example.sellingexchangeplatform.dto.response.UserProductListResponseDTO;
import org.example.sellingexchangeplatform.entity.Product;
import org.example.sellingexchangeplatform.entity.User;
import org.example.sellingexchangeplatform.entity.UserProductList;
import org.example.sellingexchangeplatform.exception.ExceptionMessage;
import org.example.sellingexchangeplatform.exception.NotFoundException;
import org.example.sellingexchangeplatform.mapper.UserProductListMapper;
import org.example.sellingexchangeplatform.repository.ProductRepository;
import org.example.sellingexchangeplatform.repository.UserProductListRepository;
import org.example.sellingexchangeplatform.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserProductListServiceImplTest {

    @Mock
    private UserProductListRepository userProductListRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserProductListMapper userProductListMapper;

    @InjectMocks
    private UserProductListServiceImpl userProductListService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserProductList_UserFound() {
        Long userId = 1L;
        User user = new User();
        UserProductList userProductList = new UserProductList();
        userProductList.setUser(user);
        userProductList.setProduct(new Product());
        userProductList.setAddedDate(LocalDateTime.now());
        List<UserProductList> userProductLists = Collections.singletonList(userProductList);
        UserProductListResponseDTO dto = new UserProductListResponseDTO();

        when(userRepository.existsById(userId)).thenReturn(true);
        when(userProductListRepository.findByUserId(userId)).thenReturn(userProductLists);
        when(userProductListMapper.toDto(userProductList)).thenReturn(dto);

        List<UserProductListResponseDTO> response = userProductListService.getUserProductList(userId);

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(dto, response.get(0));
        verify(userRepository, times(1)).existsById(userId);
        verify(userProductListRepository, times(1)).findByUserId(userId);
    }

    @Test
    void testGetUserProductList_UserNotFound() {
        Long userId = 1L;

        when(userRepository.existsById(userId)).thenReturn(false);

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> userProductListService.getUserProductList(userId));

        assertEquals(String.format(ExceptionMessage.USER_NOT_FOUND.getMessage(), userId), exception.getMessage());
        verify(userRepository, times(1)).existsById(userId);
        verify(userProductListRepository, never()).findByUserId(userId);
    }

    @Test
    void testAddProductToList_Success() {
        Long userId = 1L;
        Long productId = 2L;
        User user = new User();
        Product product = new Product();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        userProductListService.addProductToList(userId, productId);

        UserProductList userProductList = new UserProductList();
        userProductList.setUser(user);
        userProductList.setProduct(product);
        userProductList.setAddedDate(LocalDateTime.now());

        verify(userProductListRepository, times(1)).save(userProductList);
    }

    @Test
    void testAddProductToList_UserNotFound() {
        Long userId = 1L;
        Long productId = 2L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> userProductListService.addProductToList(userId, productId));

        assertEquals(String.format(ExceptionMessage.USER_NOT_FOUND.getMessage(), userId), exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
        verify(productRepository, never()).findById(productId);
        verify(userProductListRepository, never()).save(any(UserProductList.class));
    }

    @Test
    void testAddProductToList_ProductNotFound() {
        Long userId = 1L;
        Long productId = 2L;
        User user = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> userProductListService.addProductToList(userId, productId));

        assertEquals(String.format(ExceptionMessage.PRODUCT_NOT_FOUND.getMessage(), productId), exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
        verify(productRepository, times(1)).findById(productId);
        verify(userProductListRepository, never()).save(any(UserProductList.class));
    }

    @Test
    void testRemoveProductFromList_Success() {
        Long userId = 1L;
        Long productId = 2L;
        UserProductList userProductList = new UserProductList();

        when(userProductListRepository.findByUserIdAndProductId(userId, productId)).thenReturn(Optional.of(userProductList));

        userProductListService.removeProductFromList(userId, productId);

        verify(userProductListRepository, times(1)).delete(userProductList);
    }

    @Test
    void testRemoveProductFromList_NotFound() {
        Long userId = 1L;
        Long productId = 2L;

        when(userProductListRepository.findByUserIdAndProductId(userId, productId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> userProductListService.removeProductFromList(userId, productId));

        assertEquals(String.format(ExceptionMessage.NOT_FOUND.getMessage(), "User product list"), exception.getMessage());
        verify(userProductListRepository, times(1)).findByUserIdAndProductId(userId, productId);
        verify(userProductListRepository, never()).delete(any(UserProductList.class));
    }
}
