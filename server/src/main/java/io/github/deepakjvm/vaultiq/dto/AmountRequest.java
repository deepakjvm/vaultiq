package io.github.deepakjvm.vaultiq.dto;

public record AmountRequest(String accountNumber, String pin, double amount) {
}
