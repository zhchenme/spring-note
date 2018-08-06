package com.jas.demo.controller;

import com.jas.demo.service.ServiceDemo;
import com.jas.mvcframework.annotation.Autowired;
import com.jas.mvcframework.annotation.Controller;
import com.jas.mvcframework.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zc
 * @description
 * @create 2018-08-05 11:45
 */
@Controller
@RequestMapping("/mymvc")
public class ControllerDemo {
    
    @Autowired
    private ServiceDemo serviceDemo;
    
    @RequestMapping("/test")
    public void test(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().write(serviceDemo.test());
    }
}
