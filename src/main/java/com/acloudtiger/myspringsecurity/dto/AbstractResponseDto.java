package com.acloudtiger.myspringsecurity.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AbstractResponseDto {
    private static Logger log = LoggerFactory.getLogger(AbstractResponseDto.class);

    private String message;
    private int code = HttpStatus.OK.value();
    private String status = HttpStatus.OK.getReasonPhrase();

    public AbstractResponseDto() {
    }

    public AbstractResponseDto message(String message){
        this.message = message;
        return this;
    }

    public ResponseEntity<AbstractResponseDto> send(HttpStatus status){
        this.code = status.value();
        this.message = status.getReasonPhrase();
        return new ResponseEntity<AbstractResponseDto>(this, status);
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

}