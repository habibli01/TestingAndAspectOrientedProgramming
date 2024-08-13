package org.example.sellingexchangeplatform.service.impl;

import org.example.sellingexchangeplatform.dto.request.ReviewRequestDTO;
import org.example.sellingexchangeplatform.dto.response.ReviewResponseDTO;
import org.example.sellingexchangeplatform.entity.Product;
import org.example.sellingexchangeplatform.entity.Review;
import org.example.sellingexchangeplatform.entity.User;
import org.example.sellingexchangeplatform.exception.ExceptionMessage;
import org.example.sellingexchangeplatform.exception.ForbiddenException;
import org.example.sellingexchangeplatform.exception.NotFoundException;
import org.example.sellingexchangeplatform.mapper.ReviewMapper;
import org.example.sellingexchangeplatform.repository.ProductRepository;
import org.example.sellingexchangeplatform.repository.ReviewRepository;
import org.example.sellingexchangeplatform.repository.UserRepository;
import org.example.sellingexchangeplatform.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewMapper reviewMapper;

    @Override
    public ReviewResponseDTO addReview(ReviewRequestDTO reviewRequestDTO, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessage.USER_NOT_FOUND.getMessage(), userId)));
        Product product = productRepository.findById(reviewRequestDTO.getProductId())
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessage.PRODUCT_NOT_FOUND.getMessage(), reviewRequestDTO.getProductId())));

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setContent(reviewRequestDTO.getContent());
        review.setRating(reviewRequestDTO.getRating());
        review.setDate(LocalDate.from(LocalDateTime.now()));

        Review savedReview = reviewRepository.save(review);

        return reviewMapper.toDto(savedReview);
    }

    @Override
    public List<ReviewResponseDTO> getReviewsByProductId(Long productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);
        return reviews.stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteReview(Long reviewId, Long userId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessage.NOT_FOUND.getMessage(), reviewId)));

        if (!review.getUser().getId().equals(userId)) {
            throw new ForbiddenException(String.format(ExceptionMessage.FORBIDDEN.getMessage(), userId));
        }

        reviewRepository.delete(review);
    }
}
