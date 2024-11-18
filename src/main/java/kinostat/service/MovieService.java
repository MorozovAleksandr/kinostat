package kinostat.service;

import kinostat.entity.Movie;
import kinostat.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    public Page<Movie> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return movieRepository.findAll(pageable);
    }

    public Page<Movie> searchMoviesByTitle(String title, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return movieRepository.findByTitleContaining(title, pageable);
    }
}