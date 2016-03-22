package com.template.module;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BasicService<T> implements InitializingBean {


    @Autowired private SqlSession sqlSession;

    protected T dao;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.dao = sqlSession.getMapper(getMapperClass());
    }

    @SuppressWarnings("unchecked")
    public Class<T> getMapperClass() {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return (Class<T>) params[0];
    }

}
