package com.template.demeter.users;

import com.template.demeter.persistent.models.User;

import java.util.List;

/**
 * Created by eric on 15/5/21.
 */
public interface UserService {

    public User getUser(int id);

    public List<User> getSomeUsers(int age);

}
