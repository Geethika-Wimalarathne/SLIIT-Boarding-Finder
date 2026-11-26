package com.sliitboarding.boarding_finder.repository;

import com.sliitboarding.boarding_finder.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByListingIdOrderByCreatedAtDesc(Long listingId);
    List<Review> findByUserId(Long userId);
}