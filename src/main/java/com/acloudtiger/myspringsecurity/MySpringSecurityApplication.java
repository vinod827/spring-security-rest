package com.acloudtiger.myspringsecurity;

import com.acloudtiger.myspringsecurity.controller.SecuredRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.stream.Collectors;

@SpringBootApplication
public class MySpringSecurityApplication {

    private static Logger logger = LoggerFactory.getLogger(MySpringSecurityApplication.class);

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(MySpringSecurityApplication.class, args);
        applicationContext.getBeanDefinitionNames();

        logger.info("All beans loaded successfully");
        //System.out.println("All beans::"+Arrays.stream(applicationContext.getBeanDefinitionNames()).map(obj -> (obj)).collect(Collectors.toList()));


    }
}
