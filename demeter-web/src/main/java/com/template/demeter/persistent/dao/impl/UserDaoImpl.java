package com.template.demeter.persistent.dao.impl;

import com.template.demeter.persistent.dao.UserDao;
import com.template.demeter.persistent.mappers.UserMapper;
import com.template.demeter.persistent.models.User;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;
import java.util.Map;


/**
 * Created by eric on 15/5/21.
 */
public class UserDaoImpl implements UserDao, InitializingBean {

    private SqlSession sqlSession;
    private UserMapper userMapper;


    public void afterPropertiesSet() throws Exception {
        userMapper = sqlSession.getMapper(UserMapper.class);
        if(userMapper == null){
            throw new IllegalStateException("userMapper should be initialized");
        }
    }


    public User getUser(int id) {
        return userMapper.selectOne(id);
    }


    public List<User> getSomeUsers(Map<String, Object> params) {

        return null;
    }

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
}
