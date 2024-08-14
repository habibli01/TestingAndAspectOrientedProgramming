package org.example.sellingexchangeplatform.mapper;

import org.example.sellingexchangeplatform.dto.response.UserResponseDTO;
import org.example.sellingexchangeplatform.entity.Role;
import org.example.sellingexchangeplatform.entity.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Test
    void testToDto() {
        // Arrange: Create Role entities
        Role role1 = new Role();
        role1.setName("ROLE_USER");

        Role role2 = new Role();
        role2.setName("ROLE_ADMIN");

        Set<Role> roles = new HashSet<>();
        roles.add(role1);
        roles.add(role2);

        // Arrange: Create a User entity
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setEmail("test@example.com");
        user.setRoles(roles);

        // Act: Map User entity to UserResponseDTO
        UserResponseDTO dto = mapper.toDto(user);

        // Assert: Verify the mapping is correct
        assertNotNull(dto);
        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getUsername(), dto.getUsername());
        assertEquals(user.getEmail(), dto.getEmail());
        assertNotNull(dto.getRoles());
        assertEquals(2, dto.getRoles().size());
        assertTrue(dto.getRoles().contains("ROLE_USER"));
        assertTrue(dto.getRoles().contains("ROLE_ADMIN"));
    }
}
