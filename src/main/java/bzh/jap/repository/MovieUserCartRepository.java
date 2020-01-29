package bzh.jap.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bzh.jap.models.MovieUserCart;
import bzh.jap.models.MovieUserKey;

public interface MovieUserCartRepository extends JpaRepository<MovieUserCart, MovieUserKey> {

}
