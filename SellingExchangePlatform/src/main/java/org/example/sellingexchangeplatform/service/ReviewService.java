package org.example.sellingexchangeplatform.service;

import org.example.sellingexchangeplatform.dto.request.ReviewRequestDTO;
import org.example.sellingexchangeplatform.dto.response.ReviewResponseDTO;

import java.util.List;

public interface ReviewService {

    ReviewResponseDTO addReview(ReviewRequestDTO reviewRequestDTO, Long userId);

    List<ReviewResponseDTO> getReviewsByProductId(Long productId);

    void deleteReview(Long reviewId, Long userId);
}
