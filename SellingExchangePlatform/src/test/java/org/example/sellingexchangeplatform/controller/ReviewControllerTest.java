package org.example.sellingexchangeplatform.controller;

import org.example.sellingexchangeplatform.dto.request.ReviewRequestDTO;
import org.example.sellingexchangeplatform.dto.response.ReviewResponseDTO;
import org.example.sellingexchangeplatform.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReviewControllerTest {

    @InjectMocks
    private ReviewController reviewController;

    @Mock
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddReview() {
        // Arrange
        Long userId = 1L;
        ReviewRequestDTO reviewRequestDTO = new ReviewRequestDTO();
        ReviewResponseDTO reviewResponseDTO = new ReviewResponseDTO();
        when(reviewService.addReview(reviewRequestDTO, userId)).thenReturn(reviewResponseDTO);

        // Act
        ResponseEntity<ReviewResponseDTO> response = reviewController.addReview(reviewRequestDTO, userId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(reviewResponseDTO, response.getBody());
    }

    @Test
    void testGetReviewsByProductId() {
        // Arrange
        Long productId = 1L;
        List<ReviewResponseDTO> reviews = List.of(new ReviewResponseDTO(), new ReviewResponseDTO());
        when(reviewService.getReviewsByProductId(productId)).thenReturn(reviews);

        // Act
        ResponseEntity<List<ReviewResponseDTO>> response = reviewController.getReviewsByProductId(productId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(reviews, response.getBody());
    }

    @Test
    void testDeleteReview() {
        // Arrange
        Long reviewId = 1L;
        Long userId = 2L;
        doNothing().when(reviewService).deleteReview(reviewId, userId);

        // Act
        ResponseEntity<Void> response = reviewController.deleteReview(reviewId, userId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        verify(reviewService, times(1)).deleteReview(reviewId, userId);
    }
}
