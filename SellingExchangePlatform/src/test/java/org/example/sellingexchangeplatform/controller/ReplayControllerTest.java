package org.example.sellingexchangeplatform.controller;

import org.example.sellingexchangeplatform.dto.request.ReplayRequestDTO;
import org.example.sellingexchangeplatform.dto.response.ReplayResponseDTO;
import org.example.sellingexchangeplatform.service.ReplayService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReplayControllerTest {

    @InjectMocks
    private ReplayController replayController;

    @Mock
    private ReplayService replayService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddReplay() {
        // Arrange
        Long userId = 1L;
        ReplayRequestDTO replayRequestDTO = new ReplayRequestDTO();
        ReplayResponseDTO replayResponseDTO = new ReplayResponseDTO();
        when(replayService.addReplay(replayRequestDTO, userId)).thenReturn(replayResponseDTO);

        // Act
        ResponseEntity<ReplayResponseDTO> response = replayController.addReplay(replayRequestDTO, userId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(replayResponseDTO, response.getBody());
    }

    @Test
    void testGetReplaysByReviewId() {
        // Arrange
        Long reviewId = 1L;
        List<ReplayResponseDTO> replays = List.of(new ReplayResponseDTO(), new ReplayResponseDTO());
        when(replayService.getReplaysByReviewId(reviewId)).thenReturn(replays);

        // Act
        ResponseEntity<List<ReplayResponseDTO>> response = replayController.getReplaysByReviewId(reviewId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(replays, response.getBody());
    }

    @Test
    void testDeleteReplay() {
        // Arrange
        Long replayId = 1L;
        Long userId = 2L;
        doNothing().when(replayService).deleteReplay(replayId, userId);

        // Act
        ResponseEntity<Void> response = replayController.deleteReplay(replayId, userId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        verify(replayService, times(1)).deleteReplay(replayId, userId);
    }
}
