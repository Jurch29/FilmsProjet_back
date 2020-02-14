package bzh.jap.controllers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bzh.jap.models.*;
import bzh.jap.repository.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MovieUserMarkRepository movieUserMarkRepository;
	
	@Autowired
	private MovieUserCartRepository movieUserCartRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private TrailerRepository trailerRepository;
	
	@Autowired
	private ActorRepository actorRepository;
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private MovieUserCommentRepository movieUserCommentRepository;
	
	@Autowired
	private MovieCommentsRepository movieCommentsRepository;
	
	//Test par r�le
	
	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public String userAccess() {
		return "User Content.";
	}

	@GetMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	public String moderatorAccess() {
		return "Moderator Board.";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}
	
	//Test sur le model
	
	//MARIADB
	
	@GetMapping("/actor/{id}")
	public Optional<Actor> getActorTest(@PathVariable long id) {
		return actorRepository.findById(id);
	}
	
	@PostMapping("/actor")
	public String postActorTest(@RequestBody Map<String, Object> lookupRequestObject) {
		Actor actor = new Actor((String)lookupRequestObject.get("lastname"),(String)lookupRequestObject.get("firstname"));
		actorRepository.save(actor);
	    return "OK";
	}
	
	@GetMapping("/author/{id}")
	public Optional<Author> getAuthorTest(@PathVariable long id) {
		return authorRepository.findById(id);
	}
	
	@PostMapping("/author")
	public String postAuthorTest(@RequestBody Map<String, Object> lookupRequestObject) {
		Author author = new Author((String)lookupRequestObject.get("lastname"),(String)lookupRequestObject.get("firstname"));
		authorRepository.save(author); 
	    return "OK";
	}
	
	@GetMapping("/category/{id}")
	public Optional<Category> getCategoryTest(@PathVariable long id) {
		return categoryRepository.findById(id);
	}
	
	@PostMapping("/category")
	public String postCategoryTest(@RequestBody Map<String, Object> lookupRequestObject) {
		Category category = new Category((String)lookupRequestObject.get("title"));
		categoryRepository.save(category); 
	    return "OK";
	}
	
	@GetMapping("/image/{id}")
	public Optional<Image> getImageTest(@PathVariable long id) {
		return imageRepository.findById(id);
	}
	
	@PostMapping("/image")
	public String postImageTest(@RequestBody Map<String, Object> lookupRequestObject) {
		Image image = new Image((String)lookupRequestObject.get("path"));
		imageRepository.save(image); 
	    return "OK";
	}
	
	@GetMapping("/movie/{id}")
	public Optional<Movie> getMovieTest(@PathVariable long id) {
		return movieRepository.findById(id);
	}
	
	@GetMapping("/usercart")
	public Optional<MovieUserCart> getUserCartTest(@RequestParam("userId") long userId, @RequestParam("movieId") long movieId) {
		return movieUserCartRepository.findById(new MovieUserKey(movieId, userId));
	}
	
	@PostMapping("/usercart")
	public String postUserCartTest(@RequestBody Map<String, Object> lookupRequestObject) {
		Optional<Movie> mv = movieRepository.findById((long) lookupRequestObject.get("movieId"));
		Optional<User> u = userRepository.findById((long) lookupRequestObject.get("userId"));
		
		MovieUserCart m = new MovieUserCart(new MovieUserKey((long)lookupRequestObject.get("movieId"),(long)lookupRequestObject.get("userId")),(int)lookupRequestObject.get("quantity"));
		m.setMovie(mv.get());
		m.setUser(u.get());
		
		movieUserCartRepository.save(m);
		return "OK";
	}
	
	@GetMapping("/usermark")
	public Optional<MovieUserMark> getUserMarkTest(@RequestParam("userId") long userId, @RequestParam("movieId") long movieId) {
		return movieUserMarkRepository.findById(new MovieUserKey(movieId, userId));
	}
	
	@PostMapping("/usermark")
	public String postUserMarkTest(@RequestBody Map<String, Object> lookupRequestObject) {
		Optional<Movie> mv = movieRepository.findById((long) lookupRequestObject.get("movieId"));
		Optional<User> u = userRepository.findById((long) lookupRequestObject.get("userId"));
		
		MovieUserMark m = new MovieUserMark(new MovieUserKey((long)lookupRequestObject.get("movieId"),(long)lookupRequestObject.get("userId")),(double)lookupRequestObject.get("mark"));
		m.setMovie(mv.get());
		m.setUser(u.get());
		
		movieUserMarkRepository.save(m);
		return "OK";
	}

	@GetMapping("/trailer/{id}")
	public Optional<Trailer> getTrailerTest(@PathVariable long id) {
		return trailerRepository.findById(id);
	}
	
	@PostMapping("/trailer")
	public String postTrailerTest(@RequestBody Map<String, Object> lookupRequestObject) {
		Optional<Movie> mv = movieRepository.findById((long) lookupRequestObject.get("movieId"));
		
		ArrayList<Trailer> t = new ArrayList<Trailer>();
		Optional<Trailer> t1 = trailerRepository.findById((long) 40);
		Optional<Trailer> t2 = trailerRepository.findById((long) 41);
		Optional<Trailer> t3 = trailerRepository.findById((long) 45);
		t.add(t1.get());
		t.add(t2.get());
		t.add(t3.get());
		
		mv.get().setTrailers(t);
		
		movieRepository.save(mv.get());
		
		return "OK";
	}
	
	@GetMapping("/movieusercomment/{id}")
	public Optional<MovieUserComment> getMovieUserCommentTest(@PathVariable long id) {
		return movieUserCommentRepository.findById(id);
	}
	
	@GetMapping("/movieusercomment")
	public String postMovieUserCommentTest(@RequestBody Map<String, Object> lookupRequestObject) {
		MovieUserComment mvc = new MovieUserComment();
		
		Optional<Movie> mv = movieRepository.findById((long)lookupRequestObject.get("movieId"));
		Optional<User> u = userRepository.findById((long)lookupRequestObject.get("userId"));
		
		mvc.setMovieUserCommentIsDeleted(false);
		mvc.setMovieUserCommentDate(new Timestamp(System.currentTimeMillis()));
		
		mv.get().addMovieUserComment(mvc);
		u.get().addMovieUserComment(mvc);
		
		movieUserCommentRepository.save(mvc);
		
		return "OK";
	}

	@DeleteMapping("/users/{id}")
	public String deleteStudent(@PathVariable long id) {
		userRepository.deleteById(id);
		return "user "+id+" have been deleted";
	}
	
	@GetMapping("/users")
	public List<User> retrieveAllUsers() {
		return userRepository.findAll();
	}
	
	@GetMapping("/users/{id}")
	public Optional<User> retrieveUser(@PathVariable long id) {
		return userRepository.findById(id);
	}
	
	//MONGODB
	
	@GetMapping("/comments/{id}")
	public MovieComments getCommentsTest(@PathVariable String id) {
		return movieCommentsRepository.findByCommentId("1");
	}
	
	@PostMapping("/comments")
	public String postCommentsTest(@RequestBody Map<String, Object> lookupRequestObject) {
		MovieComments ms = new MovieComments();
		ms.setCommentId((String)lookupRequestObject.get("commentId"));
		ms.setCommentContent((String)lookupRequestObject.get("commentContent"));
		movieCommentsRepository.save(ms);
		return "OK";
	}
}