package bzh.jap.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import bzh.jap.models.MovieDescription;

public interface MovieDescriptionRepository extends MongoRepository<MovieDescription, String> {

	public MovieDescription findBymovieId(String movieId);
	
}
