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

        // Arrange: Create a Review entity
        Review review = new Review();
        review.setId(1L);
        review.setUser(user);  // Set the user to the review
        review.setProduct(product);  // Set the product to the review
        review.setRating(5);
        review.setContent("Great product!");

        // Act: Map Review entity to ReviewResponseDTO
        ReviewResponseDTO dto = mapper.toDto(review);

        // Assert: Verify the mapping is correct
        assertNotNull(dto);
        assertEquals(review.getId(), dto.getId());
        assertEquals(review.getRating(), dto.getRating());
        assertEquals(review.getContent(), dto.getContent());
        assertEquals(user.getUsername(), dto.getUsername());  // Check mapped username
        assertEquals(product.getName(), dto.getProductName());  // Check mapped product name
    }
}
