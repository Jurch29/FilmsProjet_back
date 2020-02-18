package bzh.jap.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bzh.jap.models.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
}
