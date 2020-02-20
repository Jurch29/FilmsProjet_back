package bzh.jap.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bzh.jap.models.MovieUserCart;
import bzh.jap.repository.MovieUserCartRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private MovieUserCartRepository movieUserCartRepository;
	
	@GetMapping("/cart/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public List<MovieUserCart> getUserCartByUserId(@PathVariable long id) {
		return movieUserCartRepository.findByEmbeddedKeyMovieUserUserId(id);
	}

}
