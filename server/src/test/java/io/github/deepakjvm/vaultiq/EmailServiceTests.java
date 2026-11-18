package io.github.deepakjvm.vaultiq;

import io.github.deepakjvm.vaultiq.service.EmailService;
import io.github.deepakjvm.vaultiq.service.OtpService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSender;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class EmailServiceTests {

    private EmailService emailService;

    @BeforeEach
    void setUp() {
        JavaMailSender mailSender = mock(JavaMailSender.class);
        emailService = new EmailService(mailSender);
    }

    @Test
    @DisplayName("Should render login alert email template with dynamic values")
    void shouldRenderLoginEmailTemplate() {
        String html = emailService.getLoginEmailTemplate("John Doe", "2026-11-18 10:30:00", "New York, USA");

        assertThat(html)
                .contains("Hi, <span>John Doe</span>")
                .contains("2026-11-18 10:30:00")
                .contains("New York, USA")
                .contains("VaultIQ")
                .contains("support@vaultiq.io");
    }

    @Test
    @DisplayName("Should render OTP login email template with dynamic values")
    void shouldRenderOtpLoginEmailTemplate() {
        String html = emailService.getOtpLoginEmailTemplate("Jane Doe", "xxx4567", "987654");

        assertThat(html)
                .contains("Hi, <span>Jane Doe</span>")
                .contains("xxx4567")
                .contains("987654")
                .contains(String.valueOf(OtpService.OTP_EXPIRY_MINUTES))
                .contains("VaultIQ")
                .contains("support@vaultiq.io");
    }

    @Test
    @DisplayName("Should render bank statement email template with dynamic values")
    void shouldRenderBankStatementEmailTemplate() {
        String html = emailService.getBankStatementEmailTemplate("Alice", "Balance: $5000.00\nDeposit: $1000.00");

        assertThat(html)
                .contains("Dear <span>Alice</span>")
                .contains("Balance: $5000.00")
                .contains("Deposit: $1000.00")
                .contains("Bank Statement");
    }

    @Test
    @DisplayName("Should properly escape HTML in user inputs to prevent injection")
    void shouldEscapeHtmlInInputs() {
        String maliciousName = "<script>alert('xss')</script>";
        String html = emailService.getLoginEmailTemplate(maliciousName, "2026-11-18", "Somewhere");

        assertThat(html)
                .doesNotContain("<script>alert('xss')</script>")
                .contains("&lt;script&gt;alert(&#39;xss&#39;)&lt;/script&gt;");
    }
}
