package io.github.deepakjvm.vaultiq.service;

import io.github.deepakjvm.vaultiq.dto.AccountResponse;
import io.github.deepakjvm.vaultiq.dto.UserResponse;

public interface DashboardService {
    UserResponse getUserDetails(String accountNumber);
    AccountResponse getAccountDetails(String accountNumber);
}