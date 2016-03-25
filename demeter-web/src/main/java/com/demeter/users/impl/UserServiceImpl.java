package com.demeter.users.impl;

import com.demeter.persistent.dao.UserDao;
import com.demeter.persistent.models.User;
import com.demeter.users.UserService;

import java.util.List;

/**
 * Created by eric on 15/5/21.
 */
public class UserServiceImpl implements UserService {


    private UserDao userDao;

    @Override
    public User getUser(int id) {
        return userDao.getUser(id);
    }

    @Override
    public List<User> getSomeUsers(int age) {
        return null;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
