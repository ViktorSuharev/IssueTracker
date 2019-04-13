package com.netcracker.edu.tms.service.mail.mailConfiguration;

import lombok.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class NotificationEnableConfiguration {

    @Value("${notification.enable}")
    private String notificationFlag;

}
