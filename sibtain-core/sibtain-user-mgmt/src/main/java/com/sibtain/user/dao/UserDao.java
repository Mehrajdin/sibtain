package com.sibtain.user.dao;

import com.sibtain.user.domain.User;

public interface UserDao {
    User getById(String id);
    User getByUserName(String userName);

    User create(User user);
}
