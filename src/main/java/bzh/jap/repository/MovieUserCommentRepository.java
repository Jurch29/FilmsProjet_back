package bzh.jap.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bzh.jap.models.MovieUserComment;

public interface MovieUserCommentRepository extends JpaRepository<MovieUserComment, Long> {

}
