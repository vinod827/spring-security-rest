package com.acloudtiger.myspringsecurity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MySpringSecurityApplication {
    private static Logger logger = LoggerFactory.getLogger(MySpringSecurityApplication.class);

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(MySpringSecurityApplication.class, args);
        applicationContext.getBeanDefinitionNames();
        logger.info("All beans loaded successfully");

    }
}
