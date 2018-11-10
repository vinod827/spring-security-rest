package com.acloudtiger.myspringsecurity.controller;

import com.acloudtiger.myspringsecurity.dto.AbstractResponseDto;
import com.acloudtiger.myspringsecurity.dto.UserDto;
import com.acloudtiger.myspringsecurity.entity.ApplicationUser;
import com.acloudtiger.myspringsecurity.repository.ApplicationUserRepository;
import com.acloudtiger.myspringsecurity.service.UserService;
import com.acloudtiger.myspringsecurity.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SecuredRestController {
    private static Logger logger = LoggerFactory.getLogger(SecuredRestController.class);

    private ApplicationUserRepository applicationUserRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserService userService;

    @Autowired
    public SecuredRestController(ApplicationUserRepository applicationUserRepository,
                                 BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService) {
        this.applicationUserRepository = applicationUserRepository;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping(path = "/user/sign-up", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<AbstractResponseDto> userSignUp(@RequestBody ApplicationUser user){
        logger.info("Entering userSignUp@SecuredRestController");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        applicationUserRepository.save(user);
        return ResponseUtil.success().body(user).message("User Signed Up successfully").send(HttpStatus.CREATED);
    }

    @PostMapping(path = "/user/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<AbstractResponseDto> userLogin(@RequestBody ApplicationUser user) {
        logger.info("Entering userLogin@SecuredRestController");
        ApplicationUser userObj = applicationUserRepository.findByUsername(user.getUsername());
        logger.info(userObj.getPassword());
        logger.info(bCryptPasswordEncoder.encode(user.getPassword()));
        if(null != userObj && userObj.getPassword().equals(bCryptPasswordEncoder.encode(user.getPassword()))){
            return ResponseUtil.success().body(user).message("User logged-in successfully").send(HttpStatus.CREATED);
        }
        return ResponseUtil.success().body(user).message("Login failed").send(HttpStatus.FORBIDDEN);

    }

    @GetMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
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
