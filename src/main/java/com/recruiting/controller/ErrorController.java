package com.recruiting.controller;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by Martha on 5/3/2017.
 */
//@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(Throwable.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(final Throwable throwable, final Model model) {
        if (throwable.getClass().isAssignableFrom(AccessDeniedException.class)) return "access-denied";
        else return "my-account";

    }
}
