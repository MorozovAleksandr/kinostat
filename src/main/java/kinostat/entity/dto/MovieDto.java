package kinostat.entity.dto;

import kinostat.entity.Category;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class MovieDto {
    private Long id;
    private String title;
    private String description;
    private LocalDate releaseDate;
    private List<Category> categories;
    private String imageBase64;
}
