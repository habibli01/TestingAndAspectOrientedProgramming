package org.example.sellingexchangeplatform.service.impl;

import org.example.sellingexchangeplatform.dto.request.ReplayRequestDTO;
import org.example.sellingexchangeplatform.dto.response.ReplayResponseDTO;
import org.example.sellingexchangeplatform.entity.Replay;
import org.example.sellingexchangeplatform.entity.Review;
import org.example.sellingexchangeplatform.entity.User;
import org.example.sellingexchangeplatform.exception.ExceptionMessage;
import org.example.sellingexchangeplatform.exception.ForbiddenException;
import org.example.sellingexchangeplatform.exception.NotFoundException;
import org.example.sellingexchangeplatform.mapper.ReplayMapper;
import org.example.sellingexchangeplatform.repository.ReplayRepository;
import org.example.sellingexchangeplatform.repository.ReviewRepository;
import org.example.sellingexchangeplatform.repository.UserRepository;
import org.example.sellingexchangeplatform.service.ReplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class ReplayServiceImpl implements ReplayService {

    @Autowired
    private ReplayRepository replayRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReplayMapper replayMapper;

    @Override
    public ReplayResponseDTO addReplay(ReplayRequestDTO replayRequestDTO, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessage.USER_NOT_FOUND.getMessage(), userId)));
        Review review = reviewRepository.findById(replayRequestDTO.getReviewId())
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessage.NOT_FOUND.getMessage(), replayRequestDTO.getReviewId())));

        Replay replay = new Replay();
        replay.setContent(replayRequestDTO.getContent());
        replay.setReview(review);
        replay.setUser(user);
        replay.setCreatedDate(LocalDateTime.now());

        Replay savedReplay = replayRepository.save(replay);

        return replayMapper.toDto(savedReplay);
    }

    @Override
    public List<ReplayResponseDTO> getReplaysByReviewId(Long reviewId) {
        List<Replay> replays = replayRepository.findByReviewId(reviewId);
        return replays.stream()
                .map(replayMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteReplay(Long replayId, Long userId) {
        Replay replay = replayRepository.findById(replayId)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessage.NOT_FOUND.getMessage(), replayId)));

        if (!replay.getReview().getUser().getId().equals(userId)) {
            throw new ForbiddenException(String.format(ExceptionMessage.FORBIDDEN.getMessage(), userId));
        }

        replayRepository.delete(replay);
    }
}
