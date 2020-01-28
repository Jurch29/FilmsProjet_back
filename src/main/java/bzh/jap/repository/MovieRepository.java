package bzh.jap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bzh.jap.models.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

}
