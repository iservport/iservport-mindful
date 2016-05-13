package com.iservport.mindful;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.ApplicationContext;

@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages = {"com.iservport.mindful.config"})
@EntityScan({"org.helianto.*.domain", "com.iservport.*.domain"})
public class Application {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
    }

}