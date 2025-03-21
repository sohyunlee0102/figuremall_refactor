package com.example.figuremall_refact.apiPayload.exception.handler;

import com.example.figuremall_refact.apiPayload.code.BaseErrorCode;
import com.example.figuremall_refact.apiPayload.exception.GeneralException;

public class ReportHandler extends GeneralException {

    public ReportHandler(BaseErrorCode errorCode) { super(errorCode); }
}
