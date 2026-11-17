package io.github.deepakjvm.vaultiq.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.deepakjvm.vaultiq.entity.PasswordResetToken;
import io.github.deepakjvm.vaultiq.entity.User;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);

    PasswordResetToken findByUser(User user);

    void deleteByToken(String token);
}
