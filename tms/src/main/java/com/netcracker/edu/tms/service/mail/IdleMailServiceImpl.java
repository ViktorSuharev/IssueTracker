package com.netcracker.edu.tms.service.mail;

import com.netcracker.edu.tms.model.Mail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IdleMailServiceImpl implements MailService {
    @Override
    public void send(List<String> addresses, Mail mail) {
        //literally does nothing, just idle stub
        //in case notification.enable in application.properties is false
    }
}
