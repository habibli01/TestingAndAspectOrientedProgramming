package org.example.sellingexchangeplatform.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    private Role role;

    @BeforeEach
    void setUp() {
        role = Role.builder()
                .name("ADMIN")
                .build();
    }

    @Test
    void testRoleCreation() {
        assertNotNull(role);
        assertEquals("ADMIN", role.getName());
    }

    @Test
    void testNameUpdate() {
        role.setName("USER");
        assertEquals("USER", role.getName());
    }

    @Test
    void testOnCreateSetsCreatedDate() {
        role.onCreate();
        assertNotNull(role.getCreatedDate());
        assertTrue(role.getCreatedDate().isBefore(LocalDateTime.now()) || role.getCreatedDate().isEqual(LocalDateTime.now()));
    }

    @Test
    void testRoleBuilder() {
        Role builtRole = Role.builder()
                .name("MODERATOR")
                .build();

        assertEquals("MODERATOR", builtRole.getName());
    }
}
