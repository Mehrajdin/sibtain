package com.sibtain.user.dao.impl;

import com.sibtain.user.dao.UserDao;
import com.sibtain.user.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;


@Transactional
@Repository("userDao")
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public User getById(String id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User getByUserName(String userName) {
        return (User) entityManager.createQuery(
                "from User where userName = :userName")
                .setParameter("userName", userName)
                .getSingleResult();

    }

    @Override
    public User create(User user) {
        entityManager.persist(user);
        return user;
    }
}
