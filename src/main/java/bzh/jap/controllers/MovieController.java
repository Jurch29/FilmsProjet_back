package bzh.jap.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bzh.jap.models.Movie;
import bzh.jap.repository.MovieRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/movie")
public class MovieController {
	
	@Autowired
	private MovieRepository movieRepository;
	
	@GetMapping("/movies")
	public List<Movie> retrieveAllMovies() {
		return movieRepository.findAll();
	}
	
	@GetMapping("/movie/{id}")
	public Optional<Movie> getMovieById(@PathVariable long id) {
		return movieRepository.findById(id);
	}

}
