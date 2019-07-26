package com.zyot.shyn.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Primary
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public String getRoleOfUser(String username, String password) {
        String role = null;
        Session session = sessionFactory.getCurrentSession();
        String getRoleQuery = "SELECT u.role FROM UserEntity as u WHERE u.username = :username AND u.password = :password";
        Query<String> query = session.createQuery(getRoleQuery, String.class)
                .setParameter("username", username)
                .setParameter("password", password);
        List<String> results = query.getResultList();
        if (!results.isEmpty())
            role = results.get(0);
        return role;
    }
}
