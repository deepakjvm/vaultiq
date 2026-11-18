package io.github.deepakjvm.vaultiq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.deepakjvm.vaultiq.dto.AccountResponse;
import io.github.deepakjvm.vaultiq.dto.UserResponse;
import io.github.deepakjvm.vaultiq.exception.NotFoundException;
import io.github.deepakjvm.vaultiq.repository.AccountRepository;
import io.github.deepakjvm.vaultiq.repository.UserRepository;
import io.github.deepakjvm.vaultiq.util.ApiMessages;

import lombok.RequiredArgsConstructor;
import lombok.val;

@Service
@RequiredArgsConstructor
public class DashboardService {
@Autowired
    private final UserRepository userRepository;
@Autowired
    private final AccountRepository accountRepository;
    public UserResponse getUserDetails(String accountNumber) {
        val user = userRepository.findByAccountAccountNumber(accountNumber)
                .orElseThrow(() -> new NotFoundException(
                        String.format(ApiMessages.USER_NOT_FOUND_BY_ACCOUNT.getMessage(), accountNumber)));

        return new UserResponse(user);
    }
    public AccountResponse getAccountDetails(String accountNumber) {
        val account = accountRepository.findByAccountNumber(accountNumber);
        if (account == null) {
            throw new NotFoundException(String.format(ApiMessages.USER_NOT_FOUND_BY_ACCOUNT.getMessage(), accountNumber));
        }

        return new AccountResponse(account);
    }

}
