package io.github.deepakjvm.vaultiq.dto;

public record FundTransferRequest(String sourceAccountNumber, String targetAccountNumber, double amount, String pin) {
}
