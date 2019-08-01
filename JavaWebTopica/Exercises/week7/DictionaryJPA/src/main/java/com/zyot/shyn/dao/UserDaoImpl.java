package com.zyot.shyn.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Primary
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public String getRoleOfUser(String username, String password) {
        String role = null;
        String getRoleQuery = "SELECT u.role FROM UserEntity as u WHERE u.username = :username AND u.password = :password";
        TypedQuery<String> query = entityManager.createQuery(getRoleQuery, String.class)
                .setParameter("username", username)
                .setParameter("password", password);
        List<String> results = query.getResultList();
        if (!results.isEmpty())
            role = results.get(0);
        return role;
    }
}
