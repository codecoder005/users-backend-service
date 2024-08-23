package com.company.config;

import com.company.service.impl.EmailServiceImpl;
import com.flagsmith.FlagsmithClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

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

    @Bean
    @Primary
    public SecurityFilterChain testSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/**").permitAll();
                }).build();
    }
}
