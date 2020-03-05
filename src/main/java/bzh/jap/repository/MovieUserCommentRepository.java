package bzh.jap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import bzh.jap.models.MovieUserComment;

public interface MovieUserCommentRepository extends JpaRepository<MovieUserComment, Long> {
	public List<MovieUserComment> findBymovieMovieId(long id);
	public List<MovieUserComment> findByuserUserId(long id);
}
