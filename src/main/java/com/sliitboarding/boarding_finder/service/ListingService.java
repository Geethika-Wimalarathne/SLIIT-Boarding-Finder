package com.sliitboarding.boarding_finder.service;

import com.sliitboarding.boarding_finder.entity.Listing;
import com.sliitboarding.boarding_finder.entity.User;
import com.sliitboarding.boarding_finder.enums.ListingStatus;
import com.sliitboarding.boarding_finder.repository.ListingRepository;
import com.sliitboarding.boarding_finder.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ListingService {

    private final ListingRepository listingRepository;
    private final UserRepository userRepository;

    public ListingService(ListingRepository listingRepository, UserRepository userRepository) {
        this.listingRepository = listingRepository;
        this.userRepository = userRepository;
    }

    public Listing createListing(Long ownerId, Listing listing) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Owner not found with id: " + ownerId));
        listing.setOwner(owner);
        listing.setStatus(ListingStatus.PENDING);
        return listingRepository.save(listing);
    }

    public List<Listing> getApprovedListings(BigDecimal maxPrice, BigDecimal maxDistanceKm) {
        if (maxPrice != null && maxDistanceKm != null) {
            return listingRepository.findByStatusAndPriceLessThanEqualAndDistanceKmLessThanEqual(
                    ListingStatus.APPROVED, maxPrice, maxDistanceKm);
        }
        return listingRepository.findByStatus(ListingStatus.APPROVED);
    }

    public List<Listing> getAllListings() {
        return listingRepository.findAll();
    }

    public List<Listing> getListingsByOwner(Long ownerId) {
        return listingRepository.findByOwnerId(ownerId);
    }

    public Optional<Listing> findById(Long id) {
        return listingRepository.findById(id);
    }

    public Listing updateStatus(Long id, ListingStatus status) {
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Listing not found with id: " + id));
        listing.setStatus(status);
        return listingRepository.save(listing);
    }

    public void deleteListing(Long id) {
        if (!listingRepository.existsById(id)) {
            throw new IllegalArgumentException("Listing not found with id: " + id);
        }
        listingRepository.deleteById(id);
    }
}