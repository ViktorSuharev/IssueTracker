package com.netcracker.edu.tms.mail.service.mail;

import com.netcracker.edu.tms.mail.model.Mail;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@NoArgsConstructor
@AllArgsConstructor
public class    MailSender implements Runnable {
    private Session currentSession;
    private String usernameForSession;
    private String address;
    private Mail mail;
    private static final Logger LOGGER = LogManager.getLogger(MailSender.class);

    @Override
    public void run() {
        try {
            System.out.println("BEGINING\n");
            Message message = new MimeMessage(currentSession);
            System.out.println(message);
            message.setFrom(new InternetAddress(usernameForSession));
            System.out.println(message.getFrom());
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(address));
            System.out.println("AFTER SETRECIPIENTS\n");
            message.setSubject(mail.getSubject());
            System.out.println(message.getSubject());
            message.setText(mail.getBody());
            System.out.println("AFTER SETTEXT\n");
            System.out.println("MESSAGE: "+ message);
            Transport.send(message);
        } catch (MessagingException e) {
            LOGGER.error("Message exeption in MailSender with stack trace: ", e.getMessage());
        }
    }
}
