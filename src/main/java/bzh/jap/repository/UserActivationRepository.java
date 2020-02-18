package bzh.jap.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bzh.jap.models.UserActivation;

public interface UserActivationRepository extends JpaRepository<UserActivation, Long> {
}
