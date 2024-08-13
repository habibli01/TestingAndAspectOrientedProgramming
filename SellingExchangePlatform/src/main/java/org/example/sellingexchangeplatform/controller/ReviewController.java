package org.example.sellingexchangeplatform.controller;

import org.example.sellingexchangeplatform.dto.request.ReviewRequestDTO;
import org.example.sellingexchangeplatform.dto.response.ReviewResponseDTO;
import org.example.sellingexchangeplatform.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/{userId}/add")
    public ResponseEntity<ReviewResponseDTO> addReview(@RequestBody ReviewRequestDTO reviewRequestDTO, @PathVariable Long userId) {
        ReviewResponseDTO reviewResponseDTO = reviewService.addReview(reviewRequestDTO, userId);
        return ResponseEntity.ok(reviewResponseDTO);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsByProductId(@PathVariable Long productId) {
        List<ReviewResponseDTO> reviews = reviewService.getReviewsByProductId(productId);
        return ResponseEntity.ok(reviews);
    }

    @DeleteMapping("/{reviewId}/{userId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId, @PathVariable Long userId) {
        reviewService.deleteReview(reviewId, userId);
        return ResponseEntity.ok().build();
    }
}

