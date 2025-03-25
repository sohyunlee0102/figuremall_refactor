package com.example.figuremall_refact.apiPayload.exception.handler;

import com.example.figuremall_refact.apiPayload.code.BaseErrorCode;
import com.example.figuremall_refact.apiPayload.exception.GeneralException;

public class DeliveryHandler extends GeneralException {

    public DeliveryHandler(BaseErrorCode errorCode) { super(errorCode); }
}
