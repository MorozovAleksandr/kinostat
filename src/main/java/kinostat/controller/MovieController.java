package kinostat.controller;

import kinostat.entity.Movie;
import kinostat.entity.Rating;
import kinostat.entity.User;
import kinostat.entity.dto.MovieDto;
import kinostat.service.CategoryService;
import kinostat.service.MovieService;
import kinostat.service.RatingService;
import kinostat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    MovieService movieService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    RatingService ratingService;

    @Autowired
    UserService userService;

    @GetMapping()
    public String listMovies(Model model, @RequestParam(name = "title", required = false) String title, @RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "size", defaultValue = "5") int size) {
        Page<Movie> moviePage;
        System.out.println(title);
        if (title != null && !title.isEmpty()) {
            moviePage = movieService.searchMoviesByTitle(title, page, size);
            model.addAttribute("title", title);
        } else {
            moviePage = movieService.findPaginated(page, size);
        }
        List<MovieDto> movies = moviePage.getContent().stream().map(this::convertToDto).collect(Collectors.toList());
        model.addAttribute("movies", movies);
        model.addAttribute("moviePage", moviePage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", moviePage.getTotalPages());
        return "movies";
    }

    @GetMapping("{id}")
    public String showMovie(@PathVariable Long id, Model model) {
        Optional<Movie> movie = movieService.findMovieById(id);
        movie.ifPresent(value -> model.addAttribute("movie", convertToDto(value)));
        return "movie";
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

    @PostMapping("{id}/rate")
    public String rateMovie(@AuthenticationPrincipal User user, @PathVariable("id") Long id, @RequestParam("rating") int rating) {
        System.out.println(user);
        System.out.println(id);
        System.out.println(rating);
        Optional<Movie> movie = movieService.findMovieById(id);
        Rating newRating = new Rating();
        if (movie.isPresent()) {
            newRating.setMovie(movie.get());
            newRating.setUser(user);
            newRating.setRating(rating);
            ratingService.saveRating(newRating);
        }
        return "redirect:/movies/" + id;
    }

    private MovieDto convertToDto(Movie movie) {
        MovieDto dto = new MovieDto();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setDescription(movie.getDescription());
        dto.setReleaseDate(movie.getReleaseDate());
        dto.setCategories(movie.getCategories());
        dto.setImageBase64(Base64.getEncoder().encodeToString(movie.getImage()));
        dto.setAverageRating(movie.getAverageRating());
        return dto;
    }
}
