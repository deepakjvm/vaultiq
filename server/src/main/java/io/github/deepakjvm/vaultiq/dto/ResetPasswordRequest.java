package io.github.deepakjvm.vaultiq.dto;

public record ResetPasswordRequest(String identifier, String resetToken, String newPassword) {
}
