package com.acloudtiger.myspringsecurity.controller;

import com.acloudtiger.myspringsecurity.dto.AbstractResponseDto;
import com.acloudtiger.myspringsecurity.dto.UserDto;
import com.acloudtiger.myspringsecurity.service.UserService;
import com.acloudtiger.myspringsecurity.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SecuredRestController {
    private static Logger logger = LoggerFactory.getLogger(SecuredRestController.class);

    private UserService userService;

    @Autowired
    public SecuredRestController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(path = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<AbstractResponseDto> listAllUsers() {
        logger.info("Entering ListAllUserDetails@SecuredRestController");
        List<UserDto> users = userService.getAllUsers();

        if (users.isEmpty()) {
            return ResponseUtil.error().message("No data found").send(HttpStatus.NOT_FOUND);
        } else {
            return ResponseUtil.success().body(users).message("Found User Details").send(HttpStatus.OK);
        }

    }

    @GetMapping(path = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<AbstractResponseDto> getUserDetailById(@PathVariable("id") int id) {
        logger.info("Entering getUserDetailById@SecuredRestController");
        UserDto userDto = userService.getUserById(id);
        if (userDto == null) {
            return ResponseUtil.error().message("No User Found").send(HttpStatus.NOT_FOUND);
        } else {
            return ResponseUtil.success().body(userDto).message("User Detail Found").send(HttpStatus.OK);
        }
    }

    @PostMapping(path = "/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<AbstractResponseDto> AddUserDetail(@RequestBody UserDto user) {
        logger.info("Entering AddUserDetail@SecuredRestController");
        userService.addUser(user);
        return ResponseUtil.success().body(user).message("User created successfully !!!").send(HttpStatus.CREATED);

    }

    /*@PutMapping(path = "/user/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void UpdateUserDetail() {
        logger.info("Entering UpdateUserDetail@SecuredRestController");

    }

    @DeleteMapping(path = "/user/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteUserById() {
        logger.info("Entering deleteUserById@SecuredRestController");

    }

    @DeleteMapping(path = "/user/", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteAllUsers() {
        logger.info("Entering deleteAllUsers@SecuredRestController");

    }*/
}
