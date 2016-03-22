package com.template.demeter.persistent.mappers;

import com.template.demeter.persistent.models.User;

import java.util.List;
import java.util.Map;

/**
 * Created by eric on 15/5/21.
 */
public interface UserMapper {

    public User selectOne(int id);

    public List<User> selectUsers(Map<String, Object> params);

}
