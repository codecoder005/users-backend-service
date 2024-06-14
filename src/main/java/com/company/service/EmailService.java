package com.company.service;

public interface EmailService {
    void send(String from, String to, String subject, String body);
    void send(String from, String to, String subject, String body, boolean html);
}
