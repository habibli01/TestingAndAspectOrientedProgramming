package org.example.sellingexchangeplatform.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserTest {

    private User user;
    private Role role;
    private Product product;
    private UserProductList userProductList;
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        role = mock(Role.class);
        product = mock(Product.class);
        userProductList = mock(UserProductList.class);

        user = User.builder()
                .username("johndoe")
                .firstName("John")
                .lastName("Doe")
                .password("Password1@")
                .email("john.doe@example.com")
                .balance(100.0)
                .roles(Collections.singleton(role))
                .products(Collections.singleton(product))
                .userProductLists(Collections.singleton(userProductList))
                .build();
    }

    @Test
    void testUserCreation() {
        assertNotNull(user);
        assertEquals("johndoe", user.getUsername());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("Password1@", user.getPassword());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals(100.0, user.getBalance());
        assertEquals(1, user.getRoles().size());
        assertEquals(1, user.getProducts().size());
        assertEquals(1, user.getUserProductLists().size());
    }

    @Test
    void testUsernameValidation() {
        user.setUsername("abc");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testPasswordValidation() {
        user.setPassword("weakpass");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testEmailValidation() {
        user.setEmail("invalid-email");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testOnCreateSetsDates() {
        user.onCreate();
        assertNotNull(user.getRegistryDate());
        assertNotNull(user.getCreatedDate());
        assertTrue(user.getRegistryDate().isBefore(LocalDateTime.now()) || user.getRegistryDate().isEqual(LocalDateTime.now()));
        assertTrue(user.getCreatedDate().isBefore(LocalDateTime.now()) || user.getCreatedDate().isEqual(LocalDateTime.now()));
    }

    @Test
    void testUserBuilder() {
        User builtUser = User.builder()
                .username("janedoe")
                .firstName("Jane")
                .lastName("Doe")
                .password("Password2@")
                .email("jane.doe@example.com")
                .balance(50.0)
                .roles(Collections.singleton(role))
                .build();

        assertEquals("janedoe", builtUser.getUsername());
        assertEquals("Jane", builtUser.getFirstName());
        assertEquals("Doe", builtUser.getLastName());
        assertEquals("Password2@", builtUser.getPassword());
        assertEquals("jane.doe@example.com", builtUser.getEmail());
        assertEquals(50.0, builtUser.getBalance());
        assertEquals(1, builtUser.getRoles().size());
    }
}
