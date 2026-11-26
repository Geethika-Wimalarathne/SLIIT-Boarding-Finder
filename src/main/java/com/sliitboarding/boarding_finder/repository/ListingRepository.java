package com.sliitboarding.boarding_finder.repository;

import com.sliitboarding.boarding_finder.entity.Listing;
import com.sliitboarding.boarding_finder.enums.ListingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.math.BigDecimal;
import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Long> {
    List<Listing> findByStatus(ListingStatus status);
    List<Listing> findByOwnerId(Long ownerId);
    List<Listing> findByStatusAndPriceLessThanEqualAndDistanceKmLessThanEqual(
            ListingStatus status,
            BigDecimal maxPrice,
            BigDecimal maxDistanceKm
    );
}