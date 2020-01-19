package bzh.jap.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import bzh.jap.models.User;
import bzh.jap.models.UserActivation;

public interface UserActivationRepository extends JpaRepository<UserActivation, Long> {
	Optional<UserActivation> findByUserId(String user_id);
}
