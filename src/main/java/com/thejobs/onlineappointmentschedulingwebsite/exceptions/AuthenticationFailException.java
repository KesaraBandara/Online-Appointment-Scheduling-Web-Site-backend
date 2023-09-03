package com.thejobs.onlineappointmentschedulingwebsite.exceptions;


public class AuthenticationFailException extends IllegalArgumentException {
    public AuthenticationFailException(String msg) {
        super(msg);
    }
}