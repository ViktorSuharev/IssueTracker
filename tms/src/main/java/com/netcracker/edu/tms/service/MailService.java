package com.netcracker.edu.tms.service;

import com.netcracker.edu.tms.model.Mail;
import java.util.List;

public interface MailService {
    void send(String address, Mail mail);

    void send(List<String> addresses, Mail mail);
}
