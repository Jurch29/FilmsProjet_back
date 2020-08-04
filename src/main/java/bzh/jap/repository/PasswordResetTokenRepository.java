package bzh.jap.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import bzh.jap.models.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
	Optional<PasswordResetToken> findByUserResetToken(String user_reset_token);
}
