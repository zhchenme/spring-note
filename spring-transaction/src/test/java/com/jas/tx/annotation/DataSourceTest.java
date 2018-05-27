package com.jas.tx.annotation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试数据源
 * 
 * @author Jas
 * @create 2018-05-25 14:30
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-tx-annotation.xml"})
public class DataSourceTest {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Test
    public void datasourceTest() {
        System.out.println(jdbcTemplate.getDataSource().getClass());
    }
}
