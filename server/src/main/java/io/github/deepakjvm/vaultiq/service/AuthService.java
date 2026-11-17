package io.github.deepakjvm.vaultiq.service;

import org.springframework.http.ResponseEntity;

import io.github.deepakjvm.vaultiq.dto.OtpRequest;
import io.github.deepakjvm.vaultiq.dto.OtpVerificationRequest;
import io.github.deepakjvm.vaultiq.dto.ResetPasswordRequest;
import io.github.deepakjvm.vaultiq.entity.User;

public interface AuthService {
    public String generatePasswordResetToken(User user);

    public boolean verifyPasswordResetToken(String token, User user);

    public void deletePasswordResetToken(String token);

    public ResponseEntity<String> sendOtpForPasswordReset(OtpRequest otpRequest);

    public ResponseEntity<String> verifyOtpAndIssueResetToken(OtpVerificationRequest otpVerificationRequest);

    public ResponseEntity<String> resetPassword(ResetPasswordRequest resetPasswordRequest);

}
