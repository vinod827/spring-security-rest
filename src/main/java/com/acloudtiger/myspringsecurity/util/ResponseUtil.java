package com.acloudtiger.myspringsecurity.util;

import com.acloudtiger.myspringsecurity.dto.ErrorResponseDto;
import com.acloudtiger.myspringsecurity.dto.SuccessResponseDto;

public class ResponseUtil {

    public static SuccessResponseDto<Object> success(){
        return new SuccessResponseDto<Object>();
    }

    public static ErrorResponseDto error(){
        return new ErrorResponseDto();
    }
}
