package com.netcracker.edu.tms.service;

import com.netcracker.edu.tms.model.Mail;
import com.netcracker.edu.tms.model.Project;
import com.netcracker.edu.tms.model.User;

import java.util.List;

public interface MailService {
    void sendInvitationToNewProject(List<User> addedUsers, Project newProject);

    void send(String address, Mail mail);

    void send(List<String> addresses, Mail mail);
}
