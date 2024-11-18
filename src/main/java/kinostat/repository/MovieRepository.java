package kinostat.repository;


import kinostat.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Transactional(readOnly = true)
    Page<Movie> findAll(Pageable pageable);

    @Transactional(readOnly = true)
    Page<Movie> findByTitleContaining(String title, Pageable pageable);
}
