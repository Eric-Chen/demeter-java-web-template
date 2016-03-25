package com.demeter.users;

import com.demeter.persistent.models.User;

import java.util.List;

/**
 * Created by eric on 15/5/21.
 */
public interface UserService {

    public User getUser(int id);

    public List<User> getSomeUsers(int age);

}
