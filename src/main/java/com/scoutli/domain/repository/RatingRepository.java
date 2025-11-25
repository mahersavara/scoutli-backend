package com.scoutli.domain.repository;

import com.scoutli.domain.entity.Rating;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RatingRepository implements PanacheRepository<Rating> {
}
