package bzh.jap.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bzh.jap.models.Movie;
import bzh.jap.repository.MovieRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/movie")
public class MovieController {
	
	@Autowired
	MovieRepository movieRepository;
	
	@GetMapping("/movies")
	public List<Movie> retrieveAllUsers() {
		return movieRepository.findAll();
	}

}
