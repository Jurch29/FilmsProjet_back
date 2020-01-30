package bzh.jap.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bzh.jap.models.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
