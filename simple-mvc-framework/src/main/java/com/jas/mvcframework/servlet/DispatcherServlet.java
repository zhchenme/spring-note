package com.jas.mvcframework.servlet;

import com.jas.mvcframework.annotation.Autowired;
import com.jas.mvcframework.annotation.Controller;
import com.jas.mvcframework.annotation.RequestMapping;
import com.jas.mvcframework.annotation.Service;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

/**
 * @author zc
 * @description
 * @create 2018-08-05 11:56
 */
public class DispatcherServlet extends HttpServlet {
    /**
     * 封装配置信息的 Properties
     */
    private Properties properties = new Properties();
    
    /**
     * 保存所有扫描类的全限定名
     */
    private List<String> classNameList = new ArrayList<>();
    
    /**
     * 封装 Bean 的 IOC Map 
     */
    private Map<String, Object> iocMap = new HashMap<>();

    /**
     * url 与方法映射的 map
     */
    private Map<String, Method> handlerMapping = new HashMap<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        // 1、加载配置文件
        doLoadConfig(config.getInitParameter("contextConfigLocation"));

        // 2、根据配置文件扫描相关的类
        doScan(properties.getProperty("scanPackage"));

        // 3、初始化所有的 Bean
        doInstance();
        
        // 4、依赖注入
        doAutowired();
        
        // 5、控制器方法与路由映射
        initHandlerMapping();
    }

    private void initHandlerMapping() {
        if (iocMap.isEmpty()) {
            return;
        }
        
        for (Map.Entry<String, Object> entry : iocMap.entrySet()) {
            Class<?> clazz = entry.getValue().getClass();
            
            if (!clazz.isAnnotationPresent(Controller.class)) {
                return;
            }
            
            String baseUrl = "";
            
            if (clazz.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping requestMappingAnno = clazz.getAnnotation(RequestMapping.class);
                baseUrl = requestMappingAnno.value();
            }

            Method[] methods = clazz.getMethods();
            
            for (Method method : methods) {
                if (!method.isAnnotationPresent(RequestMapping.class)) {
                    continue;
                }

                RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                baseUrl += annotation.value();
                
                handlerMapping.put(baseUrl, method);

                System.out.println("Mapping:" + baseUrl + " " + method);
            }
            
        }
    }

    private void doAutowired() {
        if (iocMap.isEmpty()) {
            return;
        }
        
        for (Map.Entry<String, Object> entry : iocMap.entrySet()) {
            Field[] declaredFields = entry.getValue().getClass().getDeclaredFields();
            
            for (Field field : declaredFields) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    Autowired autowired = field.getAnnotation(Autowired.class);
                    String beanName = autowired.value().trim();
                    
                    if ("".equals(beanName)) {
                        beanName = field.getType().getName();
                    }
                    
                    field.setAccessible(true);
                    try {
                        field.set(entry.getValue(), iocMap.get(beanName));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        continue;
                    }
                }
            }
        }
    }

    private void doInstance() {
        if (classNameList.isEmpty()) {
            return;
        }

        try {
            for (String className : classNameList) {
                Class<?> clazz = Class.forName(className);

                /**
                 * IOC 容器规则
                 * 1、key 默认是类名首字母小写
                 * 2、如果自定义名字，使用自定义的
                 * 3、如果是接口，使用接口的类型作为 key
                 */
                if (clazz.isAnnotationPresent(Controller.class)) {
                    String beanName = firstLetter2Lower(clazz.getSimpleName());
                    
                    iocMap.put(beanName, clazz.newInstance());
                } else if(clazz.isAnnotationPresent(Service.class)) {
                    Service serviceAnno = clazz.getAnnotation(Service.class);
                    String beanName = serviceAnno.value();
                    
                    if ("".equals(beanName.trim())) {
                        beanName = firstLetter2Lower(clazz.getSimpleName());
                    }
                    
                    Object instance = clazz.newInstance();
                    
                    iocMap.put(beanName, instance);
                    
                    Class<?>[] interfaces = clazz.getInterfaces();
                    
                    for (Class c : interfaces) {
                        // key = 接口的全限定名
                        iocMap.put(c.getName(), instance);
                    }
                } else {
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String firstLetter2Lower(String simpleName) {
        char[] chars = simpleName.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    private void doScan(String scanPackage) {
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.", "/"));
        File classDir = new File(url.getFile());

        // 递归扫描所有的文件夹
        for (File file : classDir.listFiles()) {
            if (file.isDirectory()) {
                doScan(scanPackage + "." + file.getName());
            } else {
                // 获取类的全限定名
                String className = scanPackage + "." + file.getName().replaceAll(".class", "");
                
                classNameList.add(className);
            }
        }
    }

    private void doLoadConfig(String contextConfigLocation) {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);

        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != is) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        String contextpath = req.getContextPath();

        // 6、获取请求的 URL
        url = url.replace(contextpath, "").replaceAll("/+", "/");

        if (!handlerMapping.containsKey(url)) {
            resp.getWriter().write("404 Not Found");
            return;
        }

        Method method = handlerMapping.get(url);
        String[] strings = method.toString().split("\\(")[0].split("\\.");
        String beanName = firstLetter2Lower(strings[strings.length - 2]);

        // 6、利用反射机制运行方法
        try {
            method.invoke(iocMap.get(beanName), req, resp);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
