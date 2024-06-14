package com.company.config;

import com.company.service.impl.EmailServiceImpl;
import com.flagsmith.FlagsmithClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class TestSecurityConfiguration {
    @Bean
    @Primary
    public JwtDecoder jwtDecoder() {
        return mock(JwtDecoder.class);
    }

    @Bean
    @Primary
    public FlagsmithClient flagsmithTestClient() {
        return mock(FlagsmithClient.class);
    }

    @Bean
    @Primary
    public EmailServiceImpl testEmailService() {
        return mock(EmailServiceImpl.class);
    }

    @Bean
    @Primary
    public JavaMailSender testJavaMailSender() {
        return mock(JavaMailSender.class);
    }
}
