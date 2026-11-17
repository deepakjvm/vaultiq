package io.github.deepakjvm.vaultiq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.deepakjvm.vaultiq.entity.Account;
import io.github.deepakjvm.vaultiq.entity.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Token findByToken(String token);

    Token[] findAllByAccount(Account account);

    void deleteByToken(String token);
}
