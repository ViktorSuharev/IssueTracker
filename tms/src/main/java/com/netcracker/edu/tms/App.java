package com.netcracker.edu.tms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        System.setProperty("jasypt.encryptor.password", "dmitrybobryakov");
        SpringApplication.run(App.class, args);
    }

}