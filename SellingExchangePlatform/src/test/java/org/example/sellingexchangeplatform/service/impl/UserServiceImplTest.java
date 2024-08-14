package org.example.sellingexchangeplatform.service.impl;

import org.example.sellingexchangeplatform.dto.request.UserRequestDTO;
import org.example.sellingexchangeplatform.dto.response.UserResponseDTO;
import org.example.sellingexchangeplatform.entity.Role;
import org.example.sellingexchangeplatform.entity.User;
import org.example.sellingexchangeplatform.exception.BadRequestException;
import org.example.sellingexchangeplatform.exception.ExceptionMessage;
import org.example.sellingexchangeplatform.exception.NotFoundException;
import org.example.sellingexchangeplatform.mapper.UserMapper;
import org.example.sellingexchangeplatform.repository.RoleRepository;
import org.example.sellingexchangeplatform.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_Success() {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUsername("testuser");
        userRequestDTO.setFirstName("Test");
        userRequestDTO.setLastName("User");
        userRequestDTO.setEmail("testuser@example.com");
        userRequestDTO.setPassword("password");

        Role role = new Role();
        role.setName("ROLE_USER");

        User user = new User();
        user.setUsername(userRequestDTO.getUsername());
        user.setFirstName(userRequestDTO.getFirstName());
        user.setLastName(userRequestDTO.getLastName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword("encodedPassword");
        user.setRoles(new HashSet<>(Set.of(role)));
        user.setBalance(0.0);

        User savedUser = new User();
        savedUser.setId(1L); // Simulate saved user with ID
        savedUser.setUsername(userRequestDTO.getUsername());
        savedUser.setFirstName(userRequestDTO.getFirstName());
        savedUser.setLastName(userRequestDTO.getLastName());
        savedUser.setEmail(userRequestDTO.getEmail());
        savedUser.setPassword("encodedPassword");
        savedUser.setRoles(new HashSet<>(Set.of(role)));
        savedUser.setBalance(0.0);

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(savedUser.getId());
        userResponseDTO.setUsername(savedUser.getUsername());
        userResponseDTO.setFirstName(savedUser.getFirstName());
        userResponseDTO.setLastName(savedUser.getLastName());
        userResponseDTO.setEmail(savedUser.getEmail());
        userResponseDTO.setBalance(savedUser.getBalance());

        when(userRepository.existsByUsername(userRequestDTO.getUsername())).thenReturn(false);
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(role));
        when(passwordEncoder.encode(userRequestDTO.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(userMapper.toDto(savedUser)).thenReturn(userResponseDTO);

        UserResponseDTO response = userService.registerUser(userRequestDTO);

        assertNotNull(response);
        assertEquals(userResponseDTO, response);
        verify(userRepository, times(1)).existsByUsername(userRequestDTO.getUsername());
        verify(roleRepository, times(1)).findByName("ROLE_USER");
        verify(passwordEncoder, times(1)).encode(userRequestDTO.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
        verify(userMapper, times(1)).toDto(savedUser);
    }

    @Test
    void testRegisterUser_UsernameAlreadyExists() {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUsername("testuser");

        when(userRepository.existsByUsername(userRequestDTO.getUsername())).thenReturn(true);

        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> userService.registerUser(userRequestDTO));

        assertEquals(String.format(ExceptionMessage.CONFLICT.getMessage(), "Username already exists"), exception.getMessage());
        verify(userRepository, times(1)).existsByUsername(userRequestDTO.getUsername());
        verify(roleRepository, never()).findByName("ROLE_USER");
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
        verify(userMapper, never()).toDto(any(User.class));
    }

    @Test
    void testGetUserById_Success() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUsername("testuser");

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(userId);
        userResponseDTO.setUsername("testuser");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userResponseDTO);

        UserResponseDTO response = userService.getUserById(userId);

        assertNotNull(response);
        assertEquals(userResponseDTO, response);
        verify(userRepository, times(1)).findById(userId);
        verify(userMapper, times(1)).toDto(user);
    }

    @Test
    void testGetUserById_NotFound() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> userService.getUserById(userId));

        assertEquals(String.format(ExceptionMessage.USER_NOT_FOUND.getMessage(), userId), exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
        verify(userMapper, never()).toDto(any(User.class));
    }

    @Test
    void testUpdateBalance_Success() {
        Long userId = 1L;
        Double amount = 50.0;
        User user = new User();
        user.setId(userId);
        user.setBalance(100.0);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        userService.updateBalance(userId, amount);

        assertEquals(150.0, user.getBalance());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUpdateBalance_InsufficientBalance() {
        Long userId = 1L;
        Double amount = -200.0;
        User user = new User();
        user.setId(userId);
        user.setBalance(100.0);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> userService.updateBalance(userId, amount));

        assertEquals(String.format(ExceptionMessage.INSUFFICIENT_BALANCE.getMessage(), userId), exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(user);
    }

    @Test
    void testGetUserByUsername_Success() {
        String username = "testuser";
        User user = new User();
        user.setUsername(username);

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userResponseDTO);

        UserResponseDTO response = userService.getUserByUsername(username);

        assertNotNull(response);
        assertEquals(userResponseDTO, response);
        verify(userRepository, times(1)).findByUsername(username);
        verify(userMapper, times(1)).toDto(user);
    }

    @Test
    void testGetUserByUsername_NotFound() {
        String username = "testuser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> userService.getUserByUsername(username));

        assertEquals(String.format(ExceptionMessage.USER_NOT_FOUND.getMessage(), username), exception.getMessage());
        verify(userRepository, times(1)).findByUsername(username);
        verify(userMapper, never()).toDto(any(User.class));
    }
}
