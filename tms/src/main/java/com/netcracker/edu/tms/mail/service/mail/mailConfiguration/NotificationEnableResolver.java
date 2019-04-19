package com.netcracker.edu.tms.mail.service.mail.mailConfiguration;

import com.netcracker.edu.tms.mail.service.mail.IdleMailServiceImpl;

import com.netcracker.edu.tms.mail.service.mail.MailService;
import com.netcracker.edu.tms.mail.service.mail.MailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class NotificationEnableResolver {

    @Autowired
    NotificationEnableConfiguration notificationEnableConfiguration;

    @Autowired
    @Qualifier(value = "mailServiceImpl")
    private MailServiceImpl mailServiceImpl;

    @Autowired
    @Qualifier(value = "idleMailServiceImpl")
    private IdleMailServiceImpl idleMailService;

    @Bean
    @Primary
    public MailService getMailService() {
        return notificationEnableConfiguration.getNotificationFlag().equals("true") ?
                mailServiceImpl : idleMailService;
    }

}
