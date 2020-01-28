package bzh.jap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bzh.jap.models.MarkKey;
import bzh.jap.models.MovieUserMark;

@Repository
public interface MovieUserMarkRepository extends JpaRepository<MovieUserMark, MarkKey> {

}
