package kinostat.controller;

import kinostat.entity.Movie;
import kinostat.entity.dto.MovieDto;
import kinostat.service.CategoryService;
import kinostat.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    MovieService movieService;

    @Autowired
    CategoryService categoryService;

    @GetMapping()
    public String listMovies(Model model, @RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "size", defaultValue = "5") int size) {
        Page<Movie> moviePage = movieService.findPaginated(page, size);
        List<MovieDto> movies = moviePage.getContent().stream().map(this::convertToDto).collect(Collectors.toList());
        model.addAttribute("moviePage", moviePage);
        model.addAttribute("movies", movies);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", moviePage.getTotalPages());
        return "movies";
    }

    @GetMapping("/add")
    public String showAddMovie(Model model) {
        model.addAttribute("movie", new Movie());
        model.addAttribute("categories", categoryService.findAll());
        return "add-movie";
    }

    @PostMapping("/add")
    public String addMovie(@ModelAttribute Movie movie, @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        movie.setImage(imageFile.getBytes());
        movieService.save(movie);
        return "redirect:/movies";
    }

    private MovieDto convertToDto(Movie movie) {
        MovieDto dto = new MovieDto();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setDescription(movie.getDescription());
        dto.setReleaseDate(movie.getReleaseDate());
        dto.setCategories(movie.getCategories());
        dto.setImageBase64(Base64.getEncoder().encodeToString(movie.getImage()));
        return dto;
    }
}
