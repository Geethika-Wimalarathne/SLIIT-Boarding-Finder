package com.sliitboarding.boarding_finder.service;

import com.sliitboarding.boarding_finder.entity.Listing;
import com.sliitboarding.boarding_finder.entity.Review;
import com.sliitboarding.boarding_finder.entity.User;
import com.sliitboarding.boarding_finder.repository.ListingRepository;
import com.sliitboarding.boarding_finder.repository.ReviewRepository;
import com.sliitboarding.boarding_finder.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ListingRepository listingRepository;
    private final UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository,
                         ListingRepository listingRepository,
                         UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.listingRepository = listingRepository;
        this.userRepository = userRepository;
    }

    public Review addReview(Long listingId, Long userId, Integer rating, String comment) {
        if (listingId == null) {
            throw new IllegalArgumentException("Listing ID cannot be null");
        }
        if (userId == null) {
            throw new IllegalArgumentException("Please login before submitting a review (User ID is missing)");
        }
        if (rating == null || rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new IllegalArgumentException("Listing not found with id: " + listingId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        Review review = new Review();
        review.setListing(listing);
        review.setUser(user);
        review.setRating(rating);
        review.setComment(comment);

        return reviewRepository.save(review);
    }

    public List<Review> getReviewsByListing(Long listingId) {
        if (listingId == null) {
            throw new IllegalArgumentException("Listing ID cannot be null");
        }
        return reviewRepository.findByListingIdOrderByCreatedAtDesc(listingId);
    }
}