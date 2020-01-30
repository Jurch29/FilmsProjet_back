package bzh.jap.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bzh.jap.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
