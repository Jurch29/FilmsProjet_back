package bzh.jap.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bzh.jap.models.MovieDescription;
import bzh.jap.repository.MovieDescriptionRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/mdb")
public class MongoDBcontroller {

	@Autowired
	private MovieDescriptionRepository movieDescriptionRepository;
	
	@GetMapping("/synopsis/{id}")
	public MovieDescription getSynopsis(@PathVariable String id) {
		return movieDescriptionRepository.findBymovieId(id);
	}
	
}
