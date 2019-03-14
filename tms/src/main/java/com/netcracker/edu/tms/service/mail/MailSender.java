package com.netcracker.edu.tms.service.mail;

import com.netcracker.edu.tms.model.Mail;
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
            Message message = new MimeMessage(currentSession);
            message.setFrom(new InternetAddress(usernameForSession));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(address));
            message.setSubject(mail.getSubject());
            message.setText(mail.getBody());

            Transport.send(message);
        } catch (MessagingException e) {
            LOGGER.error("Message exeption in MailSender with stack trace: ", e.getStackTrace());
        }
    }
}
