package org.example.sellingexchangeplatform.service.impl;

import org.example.sellingexchangeplatform.entity.Role;
import org.example.sellingexchangeplatform.entity.User;
import org.example.sellingexchangeplatform.exception.ExceptionMessage;
import org.example.sellingexchangeplatform.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername_UserFound() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password123");
        Role role = new Role();
        role.setName("ROLE_USER");
        user.setRoles(Collections.singleton(role));

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        UserDetails userDetails = userDetailsService.loadUserByUsername("testUser");

        assertNotNull(userDetails);
        assertEquals("testUser", userDetails.getUsername());
        assertEquals("password123", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_USER")));
        verify(userRepository, times(1)).findByUsername("testUser");
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        when(userRepository.findByUsername("unknownUser")).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("unknownUser"));

        assertEquals(String.format(ExceptionMessage.USER_NOT_FOUND.getMessage(), "unknownUser"), exception.getMessage());
        verify(userRepository, times(1)).findByUsername("unknownUser");
    }
}
