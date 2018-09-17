## springmvc-fileupload2download
SpringMVC 实现文件上传与下载的简单demo。

文件上传
```java
    @RequestMapping("/upload")
    public String upload(HttpServletRequest request, String description, MultipartFile file) throws Exception {
        if(!file.isEmpty()) {
            // 定义文件上传的路径
            String path = request.getServletContext().getRealPath("/images/");
            // 获得上传文件名
            String fileName = file.getOriginalFilename();
            File filepath = new File(path, fileName);
            
            // 判断路径是否存在，不存在创建
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            
            // 将上传文件保存到目标文件中
            file.transferTo(new File(path + File.separator + fileName));
            // 将上传图片描述信息与文件名保存在转发域中，用于下载
            request.setAttribute("description", description);
            request.setAttribute("fileName", fileName);
            return "success";
        } else {
            return "error";
        }
    }
```
文件下载
```java
    @RequestMapping(value="/download")
    public ResponseEntity<byte[]> download(HttpServletRequest request, String fileName)throws Exception {
        // 获得下载文件路径
        String path = request.getServletContext().getRealPath("/images/");
        // 对中文文件名进行转码
        fileName = new String(fileName.getBytes("iso-8859-1"), "UTF-8");
        File file = new File(path + File.separator + fileName);
        
        // 文件下载的时候将文件名转码成浏览器可以识别的 ASCII
        String downLoadFileName = new String(fileName.getBytes("utf-8"), "iso-8859-1");
        HttpHeaders headers = new HttpHeaders();
        
        headers.setContentDispositionFormData("attachment", downLoadFileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                headers, HttpStatus.CREATED);
    }
```
## spring-aop
基于注解与 XML 配置文件两种形式的 AOP demo。

基于 xml 配置文件的 aop 管理
```xml
    <!-- 配置切面的bean -->
    <bean id="loggingAspect" class="com.jas.aop.xml.LoggingAspect"/>
    
    <aop:config>
        <!-- 配置切点表达式 -->
        <aop:pointcut id="pointcut1" expression="execution(public void com.jas.aop.bean.PersonImpl.sayHello())"/>
        <aop:pointcut id="pointcut2" expression="execution(public void com.jas.aop.bean.PersonImpl.sayBye(String))"/>
        <!-- 配置切面 -->
        <aop:aspect ref="loggingAspect">
            <!-- 配置通知 -->
            <aop:before method="beforeMethod" pointcut-ref="pointcut1"/>
            <aop:around method="aroundMethod" pointcut-ref="pointcut2"/>
        </aop:aspect>
    </aop:config>
```
Java 类
```java
public class LoggingAspect {
    
    public void beforeMethod(JoinPoint joinPoint) {
        System.out.println("在目标方法执行之前执行" + ", 要拦截的方法是：" + joinPoint.getSignature());
    }
    
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        // 判断目标方法参数，满足条件修改参数值
        if(" See You Again".equals(args[0])) {
            args[0] = " See You Again ...";
        }
        
        // 在目标方法执行之前执行，相当于前置通知
        System.out.println("这是一个前置通知");
        // 执行目标方法
        Object result = joinPoint.proceed(args);
        // 在目标方法执行之后之后，相当于后置通知
        System.out.println("这是一个后置通知");
        
        return result;
    }
}
```
## spring-transaction
事务的两种实现形式：基于注解与 XML 配置文件

基于 xml 配置文件的事务管理
```xml
    <!-- 配置事务管理器 -->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <constructor-arg name="dataSource" ref="dataSource"/>
    </bean>
    
    <!-- 配置事务属性，比如传播属性，隔离界别,是否只读等等 -->
    <tx:advice id="txAdvice" transaction-manager="transactionManager">
        <tx:attributes>
            <!-- 对于从数据库汇中读取的数据设置为只读 -->
            <tx:method name="get*" read-only="true" />
            <tx:method name="*"/>
            <!-- 查询数据配置成只读属性 -->
        </tx:attributes>
    </tx:advice>
    
    <!-- 配置事务切入点，以及相关属性 -->
    <aop:config>
        <aop:pointcut id="txPointcut" expression="execution(* com.jas.tx.xml.service.*.*(..))" />
        <aop:advisor advice-ref="txAdvice" pointcut-ref="txPointcut"/>
    </aop:config>
```
## mybatis-plus-demo
Spring 整合 MyBatis Plus 的 demo，mp.sql 是对应的数据库表结构及数据，直接在数据库中还原即可。

MyBatis Plus Maven 依赖
```xml
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus</artifactId>
            <version>${version}</version>
        </dependency>
```
MyBatis Plus 基于 xml 的相关配置
```xml
    <!-- MP 提供的 MybatisSqlSessionFactoryBean -->
    <bean id="sqlSessionFactoryBean" class="com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean">
        <!-- 数据源 -->
        <property name="dataSource" ref="dataSource"></property>
        <!-- MybatisPlus 全局配置文件 -->
        <property name="configLocation" value="classpath:mybatis-config.xml"></property>
        <!-- 别名处理 -->
        <property name="typeAliasesPackage" value="com.jas.bean"></property>
        <!-- 注入全局MP策略配置 -->
        <property name="globalConfig" ref="globalConfiguration"></property>
        <!-- 插件注册 -->
        <property name="plugins">
            <list>
                <!-- 注册分页插件 -->
                <bean class="com.baomidou.mybatisplus.plugins.PaginationInterceptor" />
                <!-- 注入 SQL 性能分析插件 -->
                <bean class="com.baomidou.mybatisplus.plugins.PerformanceInterceptor">
                    <property name="maxTime" value="1000" />
                    <!--SQL 是否格式化 默认 false-->
                    <property name="format" value="true" />
                </bean>
            </list>
        </property>
    </bean>

    <!-- 定义 MybatisPlus 的全局策略配置-->
    <bean id ="globalConfiguration" class="com.baomidou.mybatisplus.entity.GlobalConfiguration">
        <!-- 在2.3版本以后，dbColumnUnderline 默认值就是true -->
        <property name="dbColumnUnderline" value="true"></property>
        <!-- 全局的主键策略 -->
        <property name="idType" value="0"></property>
        <!-- 全局的表前缀策略配置 -->
        <property name="tablePrefix" value="tbl_"></property>
    </bean>
    
    <!-- 
        配置 MybatisPlus 扫描 mapper 接口的路径
     -->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="com.jas.mapper"></property>
    </bean>
```
## simple-mvc-framework
使用 Java 反射、注解等技术实现的简易 Spring MVC 框架。

实现的注解有：<code>@Autowired</code>、<code>@Controller</code>、<code>@RequestMapping </code>、<code>@Service </code>  

核心的代码见 <code>DispatcherServlet</code> 类，由于代码过多这里就不贴出来了。

## springmvc-exception
SpringMVC 异常处理。
