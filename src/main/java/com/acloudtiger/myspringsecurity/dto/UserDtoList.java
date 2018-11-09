package com.acloudtiger.myspringsecurity.dto;

public class UserDtoList {

    private UserDto userDto;

    public UserDtoList(UserDto userDto) {
        this.userDto = userDto;
    }

    public UserDto getUserDto() {
        return userDto;
    }
}
