package org.example.sellingexchangeplatform.service;

import org.example.sellingexchangeplatform.dto.request.ReplayRequestDTO;
import org.example.sellingexchangeplatform.dto.response.ReplayResponseDTO;

import java.util.List;

public interface ReplayService {

    ReplayResponseDTO addReplay(ReplayRequestDTO replayRequestDTO, Long userId);

    List<ReplayResponseDTO> getReplaysByReviewId(Long reviewId);

    void deleteReplay(Long replayId, Long userId);
}
