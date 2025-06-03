package com.pcms.handler;

import com.pcms.controller.LoginController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LoginController.LoginException.class)
    public ModelAndView loginException(LoginController.LoginException e) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("error", e.getMessage());
        mav.addObject("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        mav.setViewName("error/500");  // error.htmlのパス
        mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return mav;
    }

}
