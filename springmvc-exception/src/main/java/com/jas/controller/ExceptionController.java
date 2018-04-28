package com.jas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Jas
 * @create 2018-04-28 8:54
 **/
@Controller
public class ExceptionController {
    
    @RequestMapping("/testExceptionHandler")
    public void exception1() {
        System.out.println(100/0);
    }
    
    @RequestMapping("/testSimpleMappingExceptionResolver")
    public void exception2() {
        int[] a = new int[2];
        System.out.println(a[10]);
    }
}
