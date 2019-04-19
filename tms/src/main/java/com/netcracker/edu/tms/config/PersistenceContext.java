package com.netcracker.edu.tms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages =  {
        "com.netcracker.edu.tms.user.repository",
        "com.netcracker.edu.tms.task.repository",
        "com.netcracker.edu.tms.project.repository"
})
public class PersistenceContext {
}

