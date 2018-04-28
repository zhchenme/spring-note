package com.jas.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Jas
 * @create 2018-04-28 8:50
 **/
//@ControllerAdvice
public class ExceptionHandlerTest {
    
    /*
    * 可以具体确定异常的类型或具体的某个异常
    * @ExceptionHandler(RuntimeException.class)
    */
    @ExceptionHandler(Exception.class)
    public ModelAndView handleGlobalException(Exception e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("exceptionMsg", e);
        return modelAndView;
    }
}
