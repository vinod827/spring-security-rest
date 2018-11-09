package com.acloudtiger.myspringsecurity.service;

import com.acloudtiger.myspringsecurity.dto.UserDto;

import java.util.List;

public interface UserService {

    public List<UserDto> getAllUsers();

    public UserDto getUserById(int id);

    public void addUser(UserDto user);
}
