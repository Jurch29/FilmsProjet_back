package bzh.jap.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import bzh.jap.models.MovieComments;

public interface MovieCommentsRepository extends MongoRepository<MovieComments, String> {

	public MovieComments findByCommentId(String commentId);
	
}
