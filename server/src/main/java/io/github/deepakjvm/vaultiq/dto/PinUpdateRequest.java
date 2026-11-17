package io.github.deepakjvm.vaultiq.dto;

public record PinUpdateRequest(String accountNumber, String oldPin, String newPin, String password) {
}
