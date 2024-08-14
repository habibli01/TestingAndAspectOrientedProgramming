package org.example.sellingexchangeplatform.service.impl;

import org.example.sellingexchangeplatform.dto.request.ReplayRequestDTO;
import org.example.sellingexchangeplatform.dto.response.ReplayResponseDTO;
import org.example.sellingexchangeplatform.entity.Replay;
import org.example.sellingexchangeplatform.entity.Review;
import org.example.sellingexchangeplatform.entity.User;
import org.example.sellingexchangeplatform.exception.ForbiddenException;
import org.example.sellingexchangeplatform.exception.NotFoundException;
import org.example.sellingexchangeplatform.mapper.ReplayMapper;
import org.example.sellingexchangeplatform.repository.ReplayRepository;
import org.example.sellingexchangeplatform.repository.ReviewRepository;
import org.example.sellingexchangeplatform.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReplayServiceImplTest {

    @Mock
    private ReplayRepository replayRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ReplayMapper replayMapper;

    @InjectMocks
    private ReplayServiceImpl replayService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddReplay() {
        ReplayRequestDTO requestDTO = new ReplayRequestDTO();
        requestDTO.setContent("Great product!");
        requestDTO.setReviewId(1L);

        User user = new User();
        user.setId(1L);

        Review review = new Review();
        review.setId(1L);

        Replay replay = new Replay();
        replay.setId(1L);
        replay.setContent(requestDTO.getContent());
        replay.setReview(review);
        replay.setUser(user);
        replay.setCreatedDate(LocalDateTime.now());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        when(replayRepository.save(any(Replay.class))).thenReturn(replay);
        when(replayMapper.toDto(any(Replay.class))).thenReturn(new ReplayResponseDTO());

        ReplayResponseDTO responseDTO = replayService.addReplay(requestDTO, 1L);

        assertNotNull(responseDTO);
        verify(replayRepository, times(1)).save(any(Replay.class));
    }

    @Test
    void testAddReplayUserNotFound() {
        ReplayRequestDTO requestDTO = new ReplayRequestDTO();
        requestDTO.setReviewId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> replayService.addReplay(requestDTO, 1L));
    }

    @Test
    void testAddReplayReviewNotFound() {
        ReplayRequestDTO requestDTO = new ReplayRequestDTO();
        requestDTO.setReviewId(1L);

        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(reviewRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> replayService.addReplay(requestDTO, 1L));
    }

    @Test
    void testGetReplaysByReviewId() {
        Replay replay = new Replay();
        replay.setId(1L);

        when(replayRepository.findByReviewId(1L)).thenReturn(List.of(replay));
        when(replayMapper.toDto(any(Replay.class))).thenReturn(new ReplayResponseDTO());

        List<ReplayResponseDTO> responseDTOs = replayService.getReplaysByReviewId(1L);

        assertNotNull(responseDTOs);
        assertFalse(responseDTOs.isEmpty());
        verify(replayRepository, times(1)).findByReviewId(1L);
    }

    @Test
    void testDeleteReplay() {
        Replay replay = new Replay();
        replay.setId(1L);

        User user = new User();
        user.setId(1L);

        Review review = new Review();
        review.setId(1L);
        review.setUser(user);

        replay.setReview(review);

        when(replayRepository.findById(1L)).thenReturn(Optional.of(replay));

        replayService.deleteReplay(1L, 1L);

        verify(replayRepository, times(1)).delete(replay);
    }

    @Test
    void testDeleteReplayForbidden() {
        Replay replay = new Replay();
        replay.setId(1L);

        User reviewOwner = new User();
        reviewOwner.setId(2L);

        Review review = new Review();
        review.setId(1L);
        review.setUser(reviewOwner);

        replay.setReview(review);

        when(replayRepository.findById(1L)).thenReturn(Optional.of(replay));

        assertThrows(ForbiddenException.class, () -> replayService.deleteReplay(1L, 1L));
    }

    @Test
    void testDeleteReplayNotFound() {
        when(replayRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> replayService.deleteReplay(1L, 1L));
    }
}
