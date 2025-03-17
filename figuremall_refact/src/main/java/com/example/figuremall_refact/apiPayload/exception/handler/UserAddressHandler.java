package com.example.figuremall_refact.apiPayload.exception.handler;

import com.example.figuremall_refact.apiPayload.code.BaseErrorCode;
import com.example.figuremall_refact.apiPayload.exception.GeneralException;

public class UserAddressHandler extends GeneralException {

    public UserAddressHandler(BaseErrorCode errorCode) { super(errorCode); }
}
