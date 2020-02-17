package bzh.jap.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bzh.jap.models.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUserLogin(String user_login);
	
	Optional<User> findByUserEmail(String user_email);

	Boolean existsByUserLogin(String user_login);

	Boolean existsByUserEmail(String user_email);
}
