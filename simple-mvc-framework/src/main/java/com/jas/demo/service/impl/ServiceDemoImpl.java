package com.jas.demo.service.impl;

import com.jas.mvcframework.annotation.Service;
import com.jas.demo.service.ServiceDemo;

/**
 * @author zc
 * @description
 * @create 2018-08-05 11:51
 */
@Service
public class ServiceDemoImpl implements ServiceDemo {
    @Override
    public String test() {
        return "My mvc framework run";
    }
}
