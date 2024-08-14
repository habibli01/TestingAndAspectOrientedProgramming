package org.example.sellingexchangeplatform.mapper;

import org.example.sellingexchangeplatform.dto.response.ReviewResponseDTO;
import org.example.sellingexchangeplatform.entity.Review;
import org.example.sellingexchangeplatform.entity.User;
import org.example.sellingexchangeplatform.entity.Product;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class ReviewMapperTest {

    private final ReviewMapper mapper = Mappers.getMapper(ReviewMapper.class);

    @Test
    void testToDto() {
        // Arrange: Create User and Product entities
        User user = new User();
        user.setUsername("testUser");

        Product product = new Product();
        product.setName("Test Product");

        // Arrange: Create a Review entity (assuming comment is set via constructor)
        Review review = new Review();

        // Act: Map Review entity to ReviewResponseDTO
        ReviewResponseDTO dto = mapper.toDto(review);

        // Assert: Verify the mapping is correct
        assertNotNull(dto);
        assertEquals(review.getId(), dto.getId());
        assertEquals(review.getRating(), dto.getRating());
        assertEquals(review.getUser().getUsername(), dto.getUsername());
        assertEquals(review.getProduct().getName(), dto.getProductName());
    }
}
