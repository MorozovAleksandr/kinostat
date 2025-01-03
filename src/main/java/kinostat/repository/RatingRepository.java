package kinostat.repository;

import kinostat.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findByMovieIdAndUserId(Long movieId, Long userId);
}
