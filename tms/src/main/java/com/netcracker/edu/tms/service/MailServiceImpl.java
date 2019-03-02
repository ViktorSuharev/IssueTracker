package com.netcracker.edu.tms.service;

import com.netcracker.edu.tms.model.Mail;
import com.netcracker.edu.tms.model.Project;
import com.netcracker.edu.tms.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Service;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class MailServiceImpl implements MailService {

    private Session session = null;
    @Value("${notification.mail.usernameForSession}")
    private String usernameForSession;
    @Value("${notification.mail.passwordForSession}")
    private String passwordForSession;
    private ExecutorService executor = Executors.newFixedThreadPool(10,
            new CustomizableThreadFactory("MailSender executor- "));

    @Override
    public void sendInvitationToNewProject(List<User> addedUsers, Project newProject) {
        List<String> addedUsersAddresses = new ArrayList<>();
        for (User user : addedUsers) {
            /*addedUsersAddresses.add(user.getEmail());*/
            addedUsersAddresses.add("credo007credo@gmail.com");
        }
        this.send(addedUsersAddresses, Mail.builder().subject(
                "You were invited in new project " + newProject.getName() + " over MailSenderImpl!").body(
                "Congratulations!").build());
    }

    @Override
    public void send(String address, Mail mail) {
        if (this.session == null) {
            this.setPropertiesAndGetSession();
        }
        MailSender sender = new MailSender(this.session, usernameForSession, address, mail);
        executor.execute(sender);
    }

    @Override
    public void send(List<String> addresses, Mail mail) {
        if (this.session == null) {
            this.setPropertiesAndGetSession();
        }
        for (String oneAddress : addresses) {
            MailSender sender = new MailSender(this.session, usernameForSession, oneAddress, mail);
            executor.execute(sender);
        }
    }

    private void setPropertiesAndGetSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        this.session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        System.out.println("USERNAME AND PASSWORD FOR SESSION: "+usernameForSession+" "+passwordForSession);
                        return new PasswordAuthentication(usernameForSession, passwordForSession);
                    }
                });

    }
}
