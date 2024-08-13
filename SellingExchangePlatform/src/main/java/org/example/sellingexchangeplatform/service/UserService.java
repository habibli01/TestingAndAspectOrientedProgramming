package org.example.sellingexchangeplatform.service;

import org.example.sellingexchangeplatform.dto.request.UserRequestDTO;
import org.example.sellingexchangeplatform.dto.response.UserResponseDTO;

public interface UserService {

    UserResponseDTO registerUser(UserRequestDTO userRequestDTO);

    UserResponseDTO getUserById(Long id);

    void updateBalance(Long userId, Double amount); // BigDecimal yerinə Double istifadə edilir

    UserResponseDTO getUserByUsername(String username);
}
