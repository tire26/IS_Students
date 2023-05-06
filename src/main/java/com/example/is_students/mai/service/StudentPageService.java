package com.example.is_students.mai.service;

import com.example.is_students.mai.dao.StudentPageDAO;
import com.example.is_students.mai.entity.*;

import java.time.LocalDate;
import java.util.List;

public class StudentPageService {

    private final StudentPageDAO studentPageDAO = new StudentPageDAO();

    public StudentPageService() {
    }

    public List<Mark> findMarksOfUser(User user) {
        return studentPageDAO.findMarksById(user);
    }

    public List<Lesson> getLessons(User user, LocalDate currentDate) {

        List<Lesson> lessonList;
        lessonList = studentPageDAO.getLessons(user, currentDate);
        return lessonList;
    }

    public Group getGroup(User user) {
        return studentPageDAO.getGroup(user);
    }

    public List<PhoneNumber> getPhoneNumber(User user) {
        return studentPageDAO.getPhoneNumber(user);
    }

    public List<Document> getDocuments(User user) {
        return studentPageDAO.getDocuments(user);
    }

    public CreditBook getCreditBook(User user) {
        return studentPageDAO.getCreditBook(user);
    }

    public Faculty getFaculty(User user) {
        return studentPageDAO.getFaculty(user);
    }

    public Course getCourse(User user) {
        return studentPageDAO.getCourse(user);
    }

    public Status getStatus(User user) {
        return studentPageDAO.getStatus(user);
    }
}
