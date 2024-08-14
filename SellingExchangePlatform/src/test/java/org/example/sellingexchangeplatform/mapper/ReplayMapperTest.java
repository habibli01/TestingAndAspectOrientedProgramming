package org.example.sellingexchangeplatform.mapper;

import org.example.sellingexchangeplatform.dto.response.ReplayResponseDTO;
import org.example.sellingexchangeplatform.entity.Replay;
import org.example.sellingexchangeplatform.entity.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class ReplayMapperTest {

    private final ReplayMapper mapper = Mappers.getMapper(ReplayMapper.class);

    @Test
    void testToDto() {
        // Arrange: Create a Replay entity and a User entity
        User user = new User();
        user.setUsername("testUser");

        Replay replay = new Replay();
        replay.setId(1L);
        replay.setContent("Test replay content");
        replay.setUser(user);

        // Act: Map Replay entity to ReplayResponseDTO
        ReplayResponseDTO dto = mapper.toDto(replay);

        // Assert: Verify the mapping is correct
        assertNotNull(dto);
        assertEquals(replay.getId(), dto.getId());
        assertEquals(replay.getContent(), dto.getContent());
        assertEquals(replay.getUser().getUsername(), dto.getUsername());
    }
}
