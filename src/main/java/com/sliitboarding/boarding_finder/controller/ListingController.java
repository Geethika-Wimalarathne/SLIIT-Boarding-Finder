package com.sliitboarding.boarding_finder.controller;

import com.sliitboarding.boarding_finder.entity.Listing;
import com.sliitboarding.boarding_finder.enums.ListingStatus;
import com.sliitboarding.boarding_finder.service.ListingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/listings")
@CrossOrigin(origins = "*")
public class ListingController {

    private final ListingService listingService;

    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }

    @GetMapping
    public List<Listing> getApprovedListings(
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) BigDecimal maxDistanceKm) {
        return listingService.getApprovedListings(maxPrice, maxDistanceKm);
    }

    @GetMapping("/all")
    public List<Listing> getAllListings() {
        return listingService.getAllListings();
    }

    @GetMapping("/owner/{ownerId}")
    public List<Listing> getByOwner(@PathVariable Long ownerId) {
        return listingService.getListingsByOwner(ownerId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return listingService.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/owner/{ownerId}")
    public ResponseEntity<?> createListing(@PathVariable Long ownerId, @RequestBody Listing listing) {
        try {
            if (listing.getImageUrl() != null && listing.getImageUrl().isBlank()) {
                listing.setImageUrl(null);
            }
            Listing saved = listingService.createListing(ownerId, listing);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestParam ListingStatus status) {
        try {
            return ResponseEntity.ok(listingService.updateStatus(id, status));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteListing(@PathVariable Long id) {
        try {
            listingService.deleteListing(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }
}