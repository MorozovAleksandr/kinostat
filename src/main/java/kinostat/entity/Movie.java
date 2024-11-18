package kinostat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDate releaseDate;

    @Lob
    private byte[] image;

    @ManyToMany
    @JoinTable(name = "movie_category", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rating> ratings;

    @Transient
    public double getAverageRating() {
        return ratings.stream().mapToInt(Rating::getRating).average().orElse(0.0);
    }
}
