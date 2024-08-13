package org.example.sellingexchangeplatform.controller;

import org.example.sellingexchangeplatform.dto.request.UserRequestDTO;
import org.example.sellingexchangeplatform.dto.response.UserResponseDTO;
import org.example.sellingexchangeplatform.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser() {
        // Arrange
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        when(userService.registerUser(userRequestDTO)).thenReturn(userResponseDTO);

        // Act
        ResponseEntity<UserResponseDTO> response = userController.registerUser(userRequestDTO);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(userResponseDTO, response.getBody());
    }

    @Test
    void testGetUserById() {
        // Arrange
        Long userId = 1L;
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        when(userService.getUserById(userId)).thenReturn(userResponseDTO);

        // Act
        ResponseEntity<UserResponseDTO> response = userController.getUserById(userId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(userResponseDTO, response.getBody());
    }

    @Test
    void testUpdateBalance() {
        // Arrange
        Long userId = 1L;
        Double amount = 100.0;
        doNothing().when(userService).updateBalance(userId, amount);

        // Act
        ResponseEntity<Void> response = userController.updateBalance(userId, amount);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        verify(userService, times(1)).updateBalance(userId, amount);
    }

    @Test
    void testGetUserByUsername() {
        // Arrange
        String username = "testuser";
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        when(userService.getUserByUsername(username)).thenReturn(userResponseDTO);

        // Act
        ResponseEntity<UserResponseDTO> response = userController.getUserByUsername(username);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(userResponseDTO, response.getBody());
    }
}
