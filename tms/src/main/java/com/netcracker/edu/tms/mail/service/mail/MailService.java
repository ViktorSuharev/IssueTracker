package com.netcracker.edu.tms.mail.service.mail;

import com.netcracker.edu.tms.mail.model.Mail;

import java.util.List;

public interface MailService {

    void send(List<String> addresses, Mail mail);
}
