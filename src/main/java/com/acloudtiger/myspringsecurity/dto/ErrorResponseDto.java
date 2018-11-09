package com.acloudtiger.myspringsecurity.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"code", "status", "message"})
public class ErrorResponseDto extends AbstractResponseDto {

}
