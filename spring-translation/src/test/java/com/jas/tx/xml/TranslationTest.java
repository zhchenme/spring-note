package com.jas.tx.xml;

import com.jas.tx.xml.service.GirlService2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Jas
 * @create 2018-05-25 14:50
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-tx-xml.xml"})
public class TranslationTest {

    @Autowired
    private GirlService2 girlService;
    
    @Test
    public void serviceObjectTest() {
        System.out.println(girlService.getClass());
    }
    
    @Test
    public void translationTest() {
      girlService.updateInfoById("张三", "李四", 1, 2);
    }
}
