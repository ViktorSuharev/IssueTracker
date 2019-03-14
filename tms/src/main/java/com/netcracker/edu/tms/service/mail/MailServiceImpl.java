package com.netcracker.edu.tms.service.mail;

import com.netcracker.edu.tms.model.Mail;
import com.netcracker.edu.tms.service.JasyptStarter.PropertyServiceForJasyptStarter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private PropertyServiceForJasyptStarter propertyServiceForJasyptStarter;

    private Session session = null;
    @Value("${notification.mail.host}")
    private String host;
    @Value("${notification.mail.threads}")
    private String threads;
    private ExecutorService executor;

    private static final Logger LOGGER   = LogManager.getLogger(MailServiceImpl.class);

    @PostConstruct
    private void init() {
        executor = Executors.newFixedThreadPool(Integer.valueOf(this.threads),
                new CustomizableThreadFactory("MailSender executor- %d "));
    }

    @Override
    public void send(List<String> addresses, Mail mail) {
        synchronized (this) {
            if (this.session == null) {
                this.setPropertiesAndGetSession();
            }
        }
        for (String oneAddress : addresses) {
            MailSender sender = new MailSender(this.session, host, oneAddress, mail);
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
                        LOGGER.debug("Host for session: " + host +
                                " Password : " + propertyServiceForJasyptStarter.getProperty());
                        return new PasswordAuthentication(host, propertyServiceForJasyptStarter.getProperty());
                    }
                });

    }
}
