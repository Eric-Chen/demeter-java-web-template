package com.demeter.persistent.dao;

import com.demeter.persistent.models.User;

import java.util.List;
import java.util.Map;

/**
 * Created by eric on 15/5/21.
 */
public interface UserDao {

    User selectOne(Map<String, Object> params);

    List<User> selectUsers(Map<String, Object> params);



}
