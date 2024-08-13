package org.example.sellingexchangeplatform.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewTest {

    private Review review;
    private User user;
    private Product product;
    private Replay replay;

    @BeforeEach
    void setUp() {
        user = mock(User.class);
        product = mock(Product.class);
        replay = mock(Replay.class);
        review = Review.builder()
                .user(user)
                .product(product)
                .content("This is a great product!")
                .rating(5)
                .replays(Collections.singletonList(replay))
                .build();
    }

    @Test
    void testReviewCreation() {
        assertNotNull(review);
        assertEquals(user, review.getUser());
        assertEquals(product, review.getProduct());
        assertEquals("This is a great product!", review.getContent());
        assertEquals(5, review.getRating());
        assertEquals(1, review.getReplays().size());
        assertEquals(replay, review.getReplays().get(0));
    }

    @Test
    void testReviewContentUpdate() {
        review.setContent("Updated review content");
        assertEquals("Updated review content", review.getContent());
    }

    @Test
    void testRatingUpdate() {
        review.setRating(4);
        assertEquals(4, review.getRating());
    }

    @Test
    void testOnCreateSetsDates() {
        review.onCreate();
        assertNotNull(review.getDate());
        assertNotNull(review.getCreatedDate());
        assertEquals(LocalDate.now(), review.getDate());
        assertTrue(review.getCreatedDate().isBefore(LocalDateTime.now()) || review.getCreatedDate().isEqual(LocalDateTime.now()));
    }

    @Test
    void testReviewBuilder() {
        Review builtReview = Review.builder()
                .user(user)
                .product(product)
                .content("Another great product!")
                .rating(4)
                .replays(Collections.emptyList())
                .build();

        assertEquals(user, builtReview.getUser());
        assertEquals(product, builtReview.getProduct());
        assertEquals("Another great product!", builtReview.getContent());
        assertEquals(4, builtReview.getRating());
        assertTrue(builtReview.getReplays().isEmpty());
    }

    @Test
    void testReplays() {
        review.setReplays(Collections.singletonList(replay));
        assertEquals(1, review.getReplays().size());
        assertEquals(replay, review.getReplays().get(0));
    }
}
