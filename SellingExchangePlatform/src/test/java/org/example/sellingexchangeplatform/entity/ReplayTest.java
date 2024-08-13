package org.example.sellingexchangeplatform.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReplayTest {

    private Replay replay;
    private User user;
    private Review review;

    @BeforeEach
    void setUp() {
        user = mock(User.class);
        review = mock(Review.class);
        replay = Replay.builder()
                .user(user)
                .review(review)
                .content("This is a test reply")
                .build();
    }

    @Test
    void testReplayCreation() {
        assertNotNull(replay);
        assertEquals(user, replay.getUser());
        assertEquals(review, replay.getReview());
        assertEquals("This is a test reply", replay.getContent());
    }

    @Test
    void testReplayContent() {
        replay.setContent("Updated content");
        assertEquals("Updated content", replay.getContent());
    }

    @Test
    void testOnCreateSetsCreatedDate() {
        replay.onCreate();
        assertNotNull(replay.getCreatedDate());
        assertTrue(replay.getCreatedDate().isBefore(LocalDateTime.now()) || replay.getCreatedDate().isEqual(LocalDateTime.now()));
    }

    @Test
    void testReplayBuilder() {
        Replay builtReplay = Replay.builder()
                .user(user)
                .review(review)
                .content("Another test reply")
                .build();

        assertEquals(user, builtReplay.getUser());
        assertEquals(review, builtReplay.getReview());
        assertEquals("Another test reply", builtReplay.getContent());
    }
}
