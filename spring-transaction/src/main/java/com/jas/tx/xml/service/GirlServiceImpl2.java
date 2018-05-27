package com.jas.tx.xml.service;

import com.jas.tx.dao.GirlDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Jas
 * @create 2018-05-25 15:15
 **/
@Service
public class GirlServiceImpl2 implements GirlService2 {
    
    @Autowired
    private GirlDao girlDao;
    
    @Override
    public void updateInfoById(String name1, String name2, Integer id1, Integer id2) {
        girlDao.updateInfo1(name1, id1);
        // 模拟一个异常
        int i = 1 / 0;
        girlDao.updateInfo2(name2, id2);
    }
}
