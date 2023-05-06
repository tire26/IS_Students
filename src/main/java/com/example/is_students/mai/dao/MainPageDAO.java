package com.example.is_students.mai.dao;


import com.example.is_students.mai.HibernateSessionFactoryUtil;
import com.example.is_students.mai.entity.User;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

public class MainPageDAO {

    public User findByLoginPassword(String login, String password) {
        Session session = HibernateSessionFactoryUtil.buildSessionFactory().openSession();
        User user = null;
        NativeQuery<User> query = session.createNativeQuery("SELECT * FROM postgres.public.users u where u.userlogin = :login and u.userpassword = :password", User.class);
        query.setParameter("login", login);
        query.setParameter("password", password);

        try {
            user = query.getSingleResult();
        } catch (NoResultException ignored) {

        }
        session.close();
        return user;
    }
}
