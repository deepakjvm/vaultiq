package io.github.deepakjvm.vaultiq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import io.github.deepakjvm.vaultiq.service.EmailService;
import io.github.deepakjvm.vaultiq.service.EmailServiceImpl;

@Configuration
public class TestConfig {

    @Bean
    @Primary
    public EmailService emailService() {
        return new EmailServiceImpl(new GreenMailJavaMailSender());
    }
}
