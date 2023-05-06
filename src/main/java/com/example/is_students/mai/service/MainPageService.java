package com.example.is_students.mai.service;


import com.example.is_students.mai.dao.MainPageDAO;
import com.example.is_students.mai.entity.User;

public class MainPageService {

    private final MainPageDAO mainPageDAO = new MainPageDAO();

    public MainPageService() {
    }

    public User findUser(String login, String password) {
        return mainPageDAO.findByLoginPassword(login, password);
    }

}
