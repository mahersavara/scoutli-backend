package com.scoutli.service;

import com.scoutli.api.dto.RatingDTO;
import com.scoutli.domain.entity.Discovery;
import com.scoutli.domain.entity.Rating;
import com.scoutli.domain.entity.User;
import com.scoutli.domain.repository.DiscoveryRepository;
import com.scoutli.domain.repository.RatingRepository;
import com.scoutli.domain.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class RatingService {

    @Inject
    RatingRepository ratingRepository;
    @Inject
    DiscoveryRepository discoveryRepository;
    @Inject
    UserRepository userRepository;

    @Transactional
    public RatingDTO createOrUpdateRating(Long discoveryId, RatingDTO.CreateRequest request, String userEmail) {
        log.info("Rating discovery {} by user {} with score {}", discoveryId, userEmail, request.score);
        User user = userRepository.findByEmail(userEmail);
        Discovery discovery = discoveryRepository.findById(discoveryId);

        if (user == null || discovery == null) {
            throw new IllegalArgumentException("User or Discovery not found");
        }

        Rating rating = ratingRepository.find("user.id = ?1 and discovery.id = ?2", user.getId(), discovery.getId())
                .firstResult();
        if (rating == null) {
            rating = new Rating();
            rating.setUser(user);
            rating.setDiscovery(discovery);
        }
        rating.setScore(request.score);

        ratingRepository.persist(rating);
        return toDTO(rating);
    }

    private RatingDTO toDTO(Rating rating) {
        return new RatingDTO(
                rating.getId(),
                rating.getScore(),
                rating.getUser().getEmail());
    }
}
