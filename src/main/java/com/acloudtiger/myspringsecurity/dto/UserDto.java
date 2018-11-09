package com.acloudtiger.myspringsecurity.dto;

public class UserDto {

    private int userId;
    private String firstName;
    private String lastName;

    public UserDto() {
    }

    public UserDto(int userId, String firstName, String lastName) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
