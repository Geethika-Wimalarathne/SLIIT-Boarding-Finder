package com.sliitboarding.boarding_finder.controller;

import com.sliitboarding.boarding_finder.entity.Review;
import com.sliitboarding.boarding_finder.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    public record AddReviewRequest(Long listingId, Long userId, Integer rating, String comment) {}

    @GetMapping("/listing/{listingId}")
    public List<Review> getByListing(@PathVariable Long listingId) {
        return reviewService.getReviewsByListing(listingId);
    }

    @PostMapping
    public ResponseEntity<?> addReview(@RequestBody AddReviewRequest req) {
        try {
            Review saved = reviewService.addReview(req.listingId(), req.userId(), req.rating(), req.comment());
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }
}