package com.netcracker.edu.tms.service;

import com.netcracker.edu.tms.model.Mail;
import org.springframework.stereotype.Service;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class MailServiceImpl implements MailService {

    private final String usernameForSession = "@gmail.com";
    private final String passwordForSession = "";

    ExecutorService executor = Executors.newFixedThreadPool(10);

    @Override
    public void send(String address, Mail mail) {
        Session currentSession = this.setPropertiesAndGetSession();
        MailSender sender = new MailSender(currentSession, usernameForSession, address, mail);
        executor.execute(sender);
    }

    @Override
    public void send(List<String> addresses, Mail mail) {
        Session currentSession = this.setPropertiesAndGetSession();
        for (String oneAddress : addresses) {
            MailSender sender = new MailSender(currentSession, usernameForSession, oneAddress, mail);
            executor.execute(sender);
        }
    }

    private Session setPropertiesAndGetSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        return Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(usernameForSession, passwordForSession);
                    }
                });

    }
}
