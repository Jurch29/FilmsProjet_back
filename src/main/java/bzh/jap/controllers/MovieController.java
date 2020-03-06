package bzh.jap.controllers;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bzh.jap.models.Actor;
import bzh.jap.models.Author;
import bzh.jap.models.Category;
import bzh.jap.models.Movie;
import bzh.jap.models.MovieComments;
import bzh.jap.models.MovieUserComment;
import bzh.jap.models.User;
import bzh.jap.payload.MessageResponse;
import bzh.jap.repository.ActorRepository;
import bzh.jap.repository.AuthorRepository;
import bzh.jap.repository.CategoryRepository;
import bzh.jap.repository.MovieCommentsRepository;
import bzh.jap.repository.MovieRepository;
import bzh.jap.repository.MovieUserCommentRepository;
import bzh.jap.repository.UserRepository;

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
	
	@Autowired
	private MovieUserCommentRepository movieUserCommentRepository;
	
	@Autowired
	private MovieCommentsRepository movieCommentRepository;
	
	@Autowired
	private UserRepository userRepository;
	
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
	
	@GetMapping("/comments/{id}")
	public List<MovieUserComment> getAllCommentsByMovieId(@PathVariable long id) {
		return movieUserCommentRepository.findBymovieMovieId(id);
	}
	
	@GetMapping("/commentcontent/{id}")
	public MovieComments getCommentContentByCommentId(@PathVariable String id) {
		return movieCommentRepository.findBycommentId(id);
	}
	
	@GetMapping("/commentuser/{id}")
	public User getMovieByCommentId(@PathVariable long id) {
		return userRepository.findBymovieUserCommentsMovieUserCommentId(id);
	}
	
	@GetMapping("/gettopfive")
	public List<Movie> gettop(){
		return movieRepository.findTopFiveMovies();
	}
	
	@PostMapping("/addcommenttomovie")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> addCommentToMovie(@RequestBody Map<String, Object> lookupRequestObject) {
		long movieId = ((Number) lookupRequestObject.get("movieId")).longValue();
		long userId = ((Number) lookupRequestObject.get("userId")).longValue();
		String commentContent = ((String) lookupRequestObject.get("content")).toString();
		
		Optional<Movie> mv = movieRepository.findById(movieId);
		Optional<User> u = userRepository.findById(userId);
		
		MovieUserComment comment = new MovieUserComment(u.get(), mv.get(), new Timestamp(System.currentTimeMillis()), false);
		
		movieUserCommentRepository.save(comment);
		
		MovieComments movieCommentContent = new MovieComments(String.valueOf(comment.getMovieUserCommentId()), commentContent);
		
		movieCommentRepository.save(movieCommentContent);

		return ResponseEntity.ok(new MessageResponse("OK"));
	}
}
