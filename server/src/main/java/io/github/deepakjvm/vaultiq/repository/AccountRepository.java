package io.github.deepakjvm.vaultiq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.deepakjvm.vaultiq.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByAccountNumber(String accountNumber);
}
