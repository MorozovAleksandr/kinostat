package kinostat.service;

import kinostat.entity.Rating;
import kinostat.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    @Transactional
    public Rating saveRating(Rating rating) {
        return ratingRepository.save(rating);
    }
}
