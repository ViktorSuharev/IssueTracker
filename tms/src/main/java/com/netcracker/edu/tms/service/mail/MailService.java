package com.netcracker.edu.tms.service.mail;

import com.netcracker.edu.tms.model.Mail;

import java.util.List;

public interface MailService {

    void send(List<String> addresses, Mail mail);
}
