package org.example.sellingexchangeplatform.controller;

import org.example.sellingexchangeplatform.dto.request.ReplayRequestDTO;
import org.example.sellingexchangeplatform.dto.response.ReplayResponseDTO;
import org.example.sellingexchangeplatform.service.ReplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/replays")
public class ReplayController {

    @Autowired
    private ReplayService replayService;

    @PostMapping("/{userId}/add")
    public ResponseEntity<ReplayResponseDTO> addReplay(@RequestBody ReplayRequestDTO replayRequestDTO, @PathVariable Long userId) {
        ReplayResponseDTO replayResponseDTO = replayService.addReplay(replayRequestDTO, userId);
        return ResponseEntity.ok(replayResponseDTO);
    }

    @GetMapping("/review/{reviewId}")
    public ResponseEntity<List<ReplayResponseDTO>> getReplaysByReviewId(@PathVariable Long reviewId) {
        List<ReplayResponseDTO> replays = replayService.getReplaysByReviewId(reviewId);
        return ResponseEntity.ok(replays);
    }

    @DeleteMapping("/{replayId}/{userId}")
    public ResponseEntity<Void> deleteReplay(@PathVariable Long replayId, @PathVariable Long userId) {
        replayService.deleteReplay(replayId, userId);
        return ResponseEntity.ok().build();
    }
}
