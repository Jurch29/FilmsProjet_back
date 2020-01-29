package bzh.jap.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bzh.jap.models.MovieUserKey;
import bzh.jap.models.Movie;
import bzh.jap.models.MovieUserCart;
import bzh.jap.models.MovieUserMark;
import bzh.jap.models.Trailer;
import bzh.jap.models.User;
import bzh.jap.repository.MovieRepository;
import bzh.jap.repository.MovieUserCartRepository;
import bzh.jap.repository.MovieUserMarkRepository;
import bzh.jap.repository.TrailerRepository;
import bzh.jap.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	MovieUserMarkRepository movieUserMarkRepository;
	
	@Autowired
	MovieUserCartRepository movieUserCartRepository;
	
	@Autowired
	MovieRepository movieRepository;
	
	@Autowired
	TrailerRepository trailerRepository;
	
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
	
	@DeleteMapping("/users/{id}")
	public String deleteStudent(@PathVariable long id) {
		userRepository.deleteById(id);
		return "user "+id+"have been deleted";
	}
	
	@GetMapping("/users")
	public List<User> retrieveAllUsers() {
		return userRepository.findAll();
	}
	
	@GetMapping("/users/{id}")
	public Optional<User> retrieveUser(@PathVariable long id) {
		return userRepository.findById(id);
	}
	
	@GetMapping("/usermark")
	public String markTest() {
		Optional<Movie> mv = movieRepository.findById((long) 1);
		Optional<User> u = userRepository.findById((long) 1);
		
		MovieUserMark m = new MovieUserMark(new MovieUserKey(13, 5), 4.2);
		m.setMovie(mv.get());
		m.setUser(u.get());
		
		movieUserMarkRepository.save(m);
		return "OK";
	}
	
	@GetMapping("/usercart")
	public String cartTest() {
		Optional<Movie> mv = movieRepository.findById((long) 1);
		Optional<User> u = userRepository.findById((long) 1);
		
		MovieUserCart m = new MovieUserCart(new MovieUserKey(13, 5), 3);
		m.setMovie(mv.get());
		m.setUser(u.get());
		
		movieUserCartRepository.save(m);
		return "OK";
	}
	
	@GetMapping("/trailer")
	public String trailerTest() {
		Optional<Movie> mv = movieRepository.findById((long) 23);
		
		ArrayList<Trailer> t = new ArrayList<Trailer>();
		Optional<Trailer> t1 = trailerRepository.findById((long) 40);
		Optional<Trailer> t2 = trailerRepository.findById((long) 41);
		Optional<Trailer> t3 = trailerRepository.findById((long) 42);
		t.add(t1.get());
		t.add(t2.get());
		t.add(t3.get());
		
		mv.get().setTrailers(t);
		
		movieRepository.save(mv.get());
		
		return "OK";
	}
}