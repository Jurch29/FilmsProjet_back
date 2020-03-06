package bzh.jap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import bzh.jap.models.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
	public Movie findBymovieUserCommentsMovieUserCommentId(long id);
	
	@Query(value = "SELECT * FROM Movie ORDER BY movie_mark DESC LIMIT 5", nativeQuery = true)
	List<Movie> findTopFiveMovies();
}
