package bzh.jap.controllers;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bzh.jap.models.Actor;
import bzh.jap.models.Author;
import bzh.jap.models.Category;
import bzh.jap.models.ERole;
import bzh.jap.models.Movie;
import bzh.jap.models.MovieDescription;
import bzh.jap.models.Role;
import bzh.jap.models.User;
import bzh.jap.payload.AddMovieRequest;
import bzh.jap.payload.MessageResponse;
import bzh.jap.repository.ActorRepository;
import bzh.jap.repository.AuthorRepository;
import bzh.jap.repository.CategoryRepository;
import bzh.jap.repository.MovieDescriptionRepository;
import bzh.jap.repository.MovieRepository;
import bzh.jap.repository.RoleRepository;
import bzh.jap.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/administration")
public class AdministrationController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private MovieDescriptionRepository movieDescriptionRepository;
	
	@Autowired
	private ActorRepository actorRepository;
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@DeleteMapping("/deleteuser/{id}")
	@Cascade(CascadeType.DELETE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<MessageResponse> deleteUser(@PathVariable long id) {
		userRepository.deleteById(id);
		return ResponseEntity.ok(new MessageResponse("User deleted"));
	}
	
	@PostMapping("/updateuser")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateUser(@RequestBody Map<String, Object> lookupRequestObject) {
		long userId = ((Number) lookupRequestObject.get("userid")).longValue();
		Optional<User> user = userRepository.findById(userId);
		user.get().setUserLastname((String) lookupRequestObject.get("username"));
		user.get().setUserFirstname((String) lookupRequestObject.get("firstname"));
		user.get().setUserLogin((String) lookupRequestObject.get("login"));
		user.get().setUserEmail((String) lookupRequestObject.get("email"));
		userRepository.save(user.get());
		return ResponseEntity.ok(new MessageResponse("ok"));
	}
	
	@PostMapping("/updatepassword")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateUserPassword(@RequestBody Map<String, Object> lookupRequestObject) {
		long userId = ((Number) lookupRequestObject.get("userid")).longValue();
		Optional<User> user = userRepository.findById(userId);
		user.get().setUserPassword(encoder.encode((String) lookupRequestObject.get("password")));
		userRepository.save(user.get());
		return ResponseEntity.ok(new MessageResponse("ok"));
	}
	
	@PostMapping("/adduser")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addNewUser(@RequestBody Map<String, Object> lookupRequestObject) {
		User user = new User((String) lookupRequestObject.get("username") , (String) lookupRequestObject.get("firstname"),
				(String) lookupRequestObject.get("email"),(String) lookupRequestObject.get("login"),
				encoder.encode((String) lookupRequestObject.get("password")));

		user.setUserIsDeleted(false);
		Set<Role> roles = new HashSet<>();
		
		user.setUserPassword(encoder.encode((String) lookupRequestObject.get("password")));
		Role userRole = roleRepository.findByRoleTitle(ERole.ROLE_USER)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(userRole);
		user.setRoles(roles);
		userRepository.save(user);
		return ResponseEntity.ok(new MessageResponse("ok"));
	}
	
	@DeleteMapping("/deletemovie/{id}")
	@Cascade(CascadeType.DELETE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<MessageResponse> deleteMovie(@PathVariable long id) {
		movieRepository.deleteById(id);
		return ResponseEntity.ok(new MessageResponse("Movie deleted"));
	}
	
	@PostMapping("/updatemovie")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateMovie(@RequestBody Map<String, Object> lookupRequestObject) {
		long movieId = ((Number) lookupRequestObject.get("movieId")).longValue();
		double price = Double.valueOf((String)lookupRequestObject.get("moviePrice"));
		int duration = Integer.valueOf((String)lookupRequestObject.get("movieDuration"));
		Timestamp timestamp = null;
		try {
			SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = inputFormat.parse((String)lookupRequestObject.get("movieDate"));
			date = outputFormat.parse(outputFormat.format(date));
		    timestamp = new java.sql.Timestamp(date.getTime()+TimeUnit.DAYS.toMillis(1));
		} catch(Exception e) { //this generic but you can control another types of exception
		    System.out.println(e);
		}
		Movie movie = movieRepository.findById(movieId).get();
		movie.setMovieTitle((String)lookupRequestObject.get("movieTitle"));
		movie.setMoviePrice(price);
		movie.setMovieDate(timestamp);
		movie.setMovieImagePath((String)lookupRequestObject.get("movieImagePath"));
		movie.setMovieTrailerPath((String)lookupRequestObject.get("movieTrailerPath"));
		movie.setMovieDuration(duration);
		movieRepository.save(movie);
		return ResponseEntity.ok(new MessageResponse("ok"));
	}
	
	@PostMapping("/addmovie")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addNewMovie(@Valid @RequestBody AddMovieRequest addMovieRequest) {
		Movie movie = addMovieRequest.getMovie();
		movie.setMovieFilePath("/localFile");
		movie.setMovieIsDeleted(false);
		movieRepository.save(movie);
		MovieDescription movieDescription = new MovieDescription();
		movieDescription.setMovieId(String.valueOf(movie.getMovieId()));
		movieDescription.setMovieDescription(addMovieRequest.getSynopsis());
		movieDescriptionRepository.save(movieDescription);
		return ResponseEntity.ok(new MessageResponse("ok"));
	}
	
	@PostMapping("/addactor")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addNewActor(@RequestBody Map<String, Object> lookupRequestObject) {
		Actor actor = new Actor();
		actor.setActorFirstName((String)lookupRequestObject.get("firstname"));
		actor.setActorLastName((String)lookupRequestObject.get("lastname"));
		actorRepository.save(actor);
		return ResponseEntity.ok(new MessageResponse("ok"));
	}
	
	@PostMapping("/addauthor")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addNewAuthor(@RequestBody Map<String, Object> lookupRequestObject) {
		Author author = new Author();
		author.setAuthorFirstName((String)lookupRequestObject.get("firstname"));
		author.setAuthorLastName((String)lookupRequestObject.get("lastname"));
		authorRepository.save(author);
		return ResponseEntity.ok(new MessageResponse("ok"));
	}
	
	@PostMapping("/addcategory")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addNewCategory(@RequestBody Map<String, Object> lookupRequestObject) {
		Category category = new Category();
		category.setCategoryTitle((String)lookupRequestObject.get("category"));
		categoryRepository.save(category);
		return ResponseEntity.ok(new MessageResponse("ok"));
	}
	
	@DeleteMapping("/deleteactor/{id}")
	@Cascade(CascadeType.DELETE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<MessageResponse> deleteActor(@PathVariable long id) {
		actorRepository.deleteById(id);
		return ResponseEntity.ok(new MessageResponse("Actor deleted"));
	}
	
	@DeleteMapping("/deleteauthor/{id}")
	@Cascade(CascadeType.DELETE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<MessageResponse> deleteAuthor(@PathVariable long id) {
		authorRepository.deleteById(id);
		return ResponseEntity.ok(new MessageResponse("Author deleted"));
	}
	
	@DeleteMapping("/deletecategory/{id}")
	@Cascade(CascadeType.DELETE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<MessageResponse> deleteCategory(@PathVariable long id) {
		categoryRepository.deleteById(id);
		return ResponseEntity.ok(new MessageResponse("Category deleted"));
	}
	
}
