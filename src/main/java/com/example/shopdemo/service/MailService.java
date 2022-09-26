package com.example.shopdemo.service;

import com.example.shopdemo.pojo.Mail;

import javax.mail.MessagingException;

public interface MailService {
    void send(Mail mail) throws MessagingException;
}
