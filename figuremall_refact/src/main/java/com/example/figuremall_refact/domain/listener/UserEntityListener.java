package com.example.figuremall_refact.domain.listener;

import com.example.figuremall_refact.apiPayload.code.status.ErrorStatus;
import com.example.figuremall_refact.apiPayload.exception.handler.UserHandler;
import com.example.figuremall_refact.domain.enums.Status;
import com.example.figuremall_refact.domain.user.User;
import jakarta.persistence.PostLoad;

public class UserEntityListener {

    @PostLoad
    public void filterInactiveUsers(User user) {
        if (!ListenerUtil.isListenerEnabled()) {
            return;
        }

        if (user.getStatus() == Status.INACTIVE) {
            throw new UserHandler(ErrorStatus.INACTIVE_USER);
        }
    }

}
