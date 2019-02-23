package com.netcracker.edu.tms.dao;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail implements Runnable {

    private final String username = "FROM@gmail.com";
    private final String password = "PASSWORD";
    private String projectsName;
    private String email;

    public SendMail(String projectsName, String email) {
        this.projectsName = projectsName;
        this.email = email;
    }

    @Override
    public void run() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("FROM@gmail.com"));

            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("TO@gmail.com")); //here must be this.mail
            message.setSubject("You in the team now!");
            message.setText("Dear employee, you were invited to project " + this.projectsName + " team!");

            System.out.println("Executing: " + Thread.currentThread().getName() + message.toString());
            Transport.send(message);
            System.out.println("Done: " + Thread.currentThread().getName() + message.toString());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
