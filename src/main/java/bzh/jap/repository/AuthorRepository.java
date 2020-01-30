package bzh.jap.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bzh.jap.models.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
