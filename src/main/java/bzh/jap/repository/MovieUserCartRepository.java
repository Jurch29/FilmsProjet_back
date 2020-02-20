package bzh.jap.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import bzh.jap.models.MovieUserCart;
import bzh.jap.models.MovieUserKey;

public interface MovieUserCartRepository extends JpaRepository<MovieUserCart, MovieUserKey> {
	List<MovieUserCart> findByEmbeddedKeyMovieUserUserId(long userId);
}
