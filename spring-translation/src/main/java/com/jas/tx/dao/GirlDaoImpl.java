package com.jas.tx.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author Jas
 * @create 2018-05-25 14:44
 **/
@Repository
public class GirlDaoImpl implements GirlDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public void updateInfo1(String name, Integer id) {
        String sql = "UPDATE girls SET name = ? WHERE id = ?;";
        jdbcTemplate.update(sql, name, id);
    }

    @Override
    public void updateInfo2(String name, Integer id) {
        String sql = "UPDATE girls SET name = ? WHERE id = ?;";
        jdbcTemplate.update(sql, name, id);
    }
}
