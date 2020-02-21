package bzh.jap.controllers;

import java.util.List;
import javax.validation.Valid;

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

import bzh.jap.models.MovieUserCart;
import bzh.jap.models.User;
import bzh.jap.payload.MergeCartRequest;
import bzh.jap.payload.MessageResponse;
import bzh.jap.repository.MovieRepository;
import bzh.jap.repository.MovieUserCartRepository;
import bzh.jap.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private MovieUserCartRepository movieUserCartRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@GetMapping("/cart/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public List<MovieUserCart> getUserCartByUserId(@PathVariable long id) {
		return movieUserCartRepository.findByEmbeddedKeyMovieUserUserId(id);
	}

	@PostMapping("/cartmerge")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> mergeCart(@Valid @RequestBody MergeCartRequest mergeCartRequest) {
		
		System.out.println(mergeCartRequest);
		
		System.out.println(mergeCartRequest.getLocalCart());
		System.out.println(mergeCartRequest.getUserId());
		
		User user = userRepository.findById(mergeCartRequest.getUserId()).get();
		
		for (int i = 0 ; i < mergeCartRequest.getLocalCart().size() ; i++) {
			mergeCartRequest.getLocalCart().get(i).setUser(user);
			mergeCartRequest.getLocalCart().get(i).setMovie(movieRepository.findById(mergeCartRequest.getLocalCart().get(i).getEmbeddedKeyMovieUser().getMovieId()).get());
		}
		
		movieUserCartRepository.saveAll(mergeCartRequest.getLocalCart());
		return ResponseEntity.ok(new MessageResponse("OK"));
	}
}
