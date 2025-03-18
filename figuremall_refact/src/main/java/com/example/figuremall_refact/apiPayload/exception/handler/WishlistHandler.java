package com.example.figuremall_refact.apiPayload.exception.handler;

import com.example.figuremall_refact.apiPayload.code.BaseErrorCode;
import com.example.figuremall_refact.apiPayload.exception.GeneralException;

public class WishlistHandler extends GeneralException {

    public WishlistHandler(BaseErrorCode errorCode) { super(errorCode); }
}
