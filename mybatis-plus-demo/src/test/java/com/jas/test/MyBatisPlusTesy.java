package com.jas.test;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jas.bean.Employee;
import com.jas.mapper.EmployeeMapper;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Jas
 * @create 2018-07-09 21:01
 **/
public class MyBatisPlusTesy {
    
    private ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    
    private EmployeeMapper employeeMapper = context.getBean("employeeMapper", EmployeeMapper.class);
    
    @Test
    public void testDataSource() throws SQLException {
        DataSource dataSource = context.getBean("dataSource", DataSource.class);
        System.out.println(dataSource.getConnection().getClass());
    }
    
    @Test
    public void getEmpByIdTest() {
        Employee employee = employeeMapper.selectById(1);
        System.out.println(employee);
    }
    
    @Test
    public void getEmpByPage() {
        Page<?> page = new Page<>(1, 5);
        List<Employee> list = employeeMapper.selectPage(page, null);
        System.out.println("总记录数：" + page.getTotal());
        System.out.println("总页数" + page.getPages());
        System.out.println(list);
    }
    
    @Test
    public void insertEmpTest() {
        Employee employee = new Employee(null, "张三", "jas@qq.com", 1, 20);
        Integer result = employeeMapper.insert(employee);
        
        System.out.println(result);
        // 无需做任何配置，在插入数据时就可以获取自增的主键值
        System.out.println("自增主键值：" + employee.getId());
    }
    
    @Test
    public void updateByIdTest() {
        Employee employee = new Employee(5, "李四", "zc@qq.com", 1, 22);
        Integer result = employeeMapper.updateById(employee);
        System.out.println(result);
        
        Employee employee2 = employeeMapper.selectById(5);
        System.out.println(employee2);
    }
    
    @Test
    public void deleteByIdTest() {
        Integer result = employeeMapper.deleteById(8);
        System.out.println(result);

        Employee employee2 = employeeMapper.selectById(8);
        System.out.println(employee2);
    }
    
    @Test
    public void selectByName() {
        EntityWrapper<Employee> wrapper = new EntityWrapper<>();
        
        // 'last_name' 与 'age' 对应数据库中的字段 
        wrapper.like("last_name", "张");
        wrapper.eq("age", 20);
        
        List<Employee> list = employeeMapper.selectList(wrapper);
        System.out.println(list);
    }
}
