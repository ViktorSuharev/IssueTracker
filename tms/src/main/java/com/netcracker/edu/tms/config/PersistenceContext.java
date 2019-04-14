package com.netcracker.edu.tms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages =  {
        "com.netcracker.edu.tms.repository"
})
public class PersistenceContext {
}

