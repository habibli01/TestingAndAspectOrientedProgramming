package org.example.sellingexchangeplatform.service.impl;

import org.example.sellingexchangeplatform.dto.request.ReviewRequestDTO;
import org.example.sellingexchangeplatform.dto.response.ReviewResponseDTO;
import org.example.sellingexchangeplatform.entity.Product;
import org.example.sellingexchangeplatform.entity.Review;
import org.example.sellingexchangeplatform.entity.User;
import org.example.sellingexchangeplatform.exception.ForbiddenException;
import org.example.sellingexchangeplatform.exception.NotFoundException;
import org.example.sellingexchangeplatform.mapper.ReviewMapper;
import org.example.sellingexchangeplatform.repository.ProductRepository;
import org.example.sellingexchangeplatform.repository.ReviewRepository;
import org.example.sellingexchangeplatform.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ReviewMapper reviewMapper;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddReview() {
        ReviewRequestDTO requestDTO = new ReviewRequestDTO();
        requestDTO.setContent("Great product!");
        requestDTO.setRating(5);
        requestDTO.setProductId(1L);

        User user = new User();
        user.setId(1L);

        Product product = new Product();
        product.setId(1L);

        Review review = new Review();
        review.setId(1L);
        review.setUser(user);
        review.setProduct(product);
        review.setContent(requestDTO.getContent());
        review.setRating(requestDTO.getRating());
        review.setDate(LocalDate.now());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(reviewRepository.save(any(Review.class))).thenReturn(review);
        when(reviewMapper.toDto(any(Review.class))).thenReturn(new ReviewResponseDTO());

        ReviewResponseDTO responseDTO = reviewService.addReview(requestDTO, 1L);

        assertNotNull(responseDTO);
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void testAddReviewUserNotFound() {
        ReviewRequestDTO requestDTO = new ReviewRequestDTO();
        requestDTO.setProductId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> reviewService.addReview(requestDTO, 1L));
    }

    @Test
    void testAddReviewProductNotFound() {
        ReviewRequestDTO requestDTO = new ReviewRequestDTO();
        requestDTO.setProductId(1L);

        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> reviewService.addReview(requestDTO, 1L));
    }

    @Test
    void testGetReviewsByProductId() {
        Review review = new Review();
        review.setId(1L);

        when(reviewRepository.findByProductId(1L)).thenReturn(List.of(review));
        when(reviewMapper.toDto(any(Review.class))).thenReturn(new ReviewResponseDTO());

        List<ReviewResponseDTO> responseDTOs = reviewService.getReviewsByProductId(1L);

        assertNotNull(responseDTOs);
        assertFalse(responseDTOs.isEmpty());
        verify(reviewRepository, times(1)).findByProductId(1L);
    }

    @Test
    void testDeleteReview() {
        Review review = new Review();
        review.setId(1L);

        User user = new User();
        user.setId(1L);
        review.setUser(user);

        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        reviewService.deleteReview(1L, 1L);

        verify(reviewRepository, times(1)).delete(review);
    }

    @Test
    void testDeleteReviewForbidden() {
        Review review = new Review();
        review.setId(1L);

        User reviewOwner = new User();
        reviewOwner.setId(2L);
        review.setUser(reviewOwner);

        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        assertThrows(ForbiddenException.class, () -> reviewService.deleteReview(1L, 1L));
    }

    @Test
    void testDeleteReviewNotFound() {
        when(reviewRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> reviewService.deleteReview(1L, 1L));
    }
}
