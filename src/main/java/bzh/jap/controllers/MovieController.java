package bzh.jap.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bzh.jap.models.Actor;
import bzh.jap.models.Author;
import bzh.jap.models.Category;
import bzh.jap.models.Movie;
import bzh.jap.repository.ActorRepository;
import bzh.jap.repository.AuthorRepository;
import bzh.jap.repository.CategoryRepository;
import bzh.jap.repository.MovieRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/movie")
public class MovieController {
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private ActorRepository actorRepository;
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@GetMapping("/movies")
	public List<Movie> retrieveAllMovies() {
		return movieRepository.findAll();
	}
	
	@GetMapping("/movie/{id}")
	public Optional<Movie> getMovieById(@PathVariable long id) {
		return movieRepository.findById(id);
	}
	
	@GetMapping("/actors")
	public List<Actor> getAllActors() {
		return actorRepository.findAll();
	}
	
	@GetMapping("/authors")
	public List<Author> getAllAuthors() {
		return authorRepository.findAll();
	}
	
	@GetMapping("/categories")
	public List<Category> getAllcategories() {
		return categoryRepository.findAll();
	}

}
