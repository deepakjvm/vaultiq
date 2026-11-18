package io.github.deepakjvm.vaultiq.service;

import java.io.File;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import jakarta.mail.MessagingException;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Autowired
    public EmailService(JavaMailSender mailSender, SpringTemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine != null ? templateEngine : createDefaultTemplateEngine();
    }

    public EmailService(JavaMailSender mailSender) {
        this(mailSender, createDefaultTemplateEngine());
    }

    public static SpringTemplateEngine createDefaultTemplateEngine() {
        val resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding("UTF-8");
        val engine = new SpringTemplateEngine();
        engine.setTemplateResolver(resolver);
        return engine;
    }

    @Async
    public CompletableFuture<Void> sendEmail(String to, String subject, String text) {
        val future = new CompletableFuture<Void>();

        try {
            val message = mailSender.createMimeMessage();
            val helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            // From address is automatically set by Spring Boot based on your properties
            helper.setSubject(subject);
            helper.setText(text, true); // Set the second parameter to true to send HTML content
            mailSender.send(message);

            log.info("Sent email to {}", to);
            future.complete(null);

        } catch (MessagingException | MailException e) {
            log.error("Failed to send email to {}", to, e);
            future.completeExceptionally(e);
        }

        return future;
    }

    public String getLoginEmailTemplate(String name, String loginTime, String loginLocation) {
        val context = new Context();
        context.setVariable("name", name);
        context.setVariable("loginTime", loginTime);
        context.setVariable("loginLocation", loginLocation);
        return templateEngine.process("mail/login-alert", context);
    }

    public String getOtpLoginEmailTemplate(String name, String accountNumber, String otp) {
        val context = new Context();
        context.setVariable("name", name);
        context.setVariable("accountNumber", accountNumber);
        context.setVariable("otp", otp);
        context.setVariable("expiryMinutes", OtpService.OTP_EXPIRY_MINUTES);
        return templateEngine.process("mail/otp-login", context);
    }

    public String getBankStatementEmailTemplate(String name, String statementText) {
        val context = new Context();
        context.setVariable("name", name);
        context.setVariable("statementText", statementText);
        return templateEngine.process("mail/bank-statement", context);
    }

    public void sendEmailWithAttachment(String to, String subject, String text, String attachmentFilePath) {
        try {
            val message = mailSender.createMimeMessage();
            val helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true); // Set the second parameter to true to send HTML content

            // Add an attachment to the email
            val attachmentFile = new File(attachmentFilePath);
            helper.addAttachment(attachmentFile.getName(), attachmentFile);

            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("Failed to send email to {}", to, e);
        }
    }
}
