package bzh.jap.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bzh.jap.models.Actor;

public interface ActorRepository extends JpaRepository<Actor, Long> {

}
