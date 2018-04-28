package com.jas.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Jas
 * @create 2018-04-28 9:38
 **/
@ResponseStatus(reason = "资源没有找到", value = HttpStatus.NOT_FOUND)
public class NotFoundExceptionHandler extends RuntimeException {
    
}
