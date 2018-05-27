package com.jas.tx.annotation.service;

import com.jas.tx.dao.GirlDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Jas
 * @create 2018-05-25 15:15
 **/
@Service
public class GirlServiceImpl implements GirlService {
    
    @Autowired
    private GirlDao girlDao;

    /**
     * 更新两次数据，在中间模拟一个异常，用于测试事务
     * 
     * 添加 @Transactional 注解
     * 在使用事务的同时还可以指定如下原则：
     *      1.事务的传播行为
     *      2.事务的隔离级别
     *      3.设置回滚与不回滚的异常
     *      4.设置是否只读
     *      5.设置事务的超时时间等
     *      
     * @param name1
     * @param name2
     * @param id1
     * @param id2
     */
    @Transactional
    @Override
    public void updateInfoById(String name1, String name2, Integer id1, Integer id2) {
        girlDao.updateInfo1(name1, id1);
        // 模拟一个异常
        int i = 1 / 0;
        girlDao.updateInfo2(name2, id2);
    }
}
