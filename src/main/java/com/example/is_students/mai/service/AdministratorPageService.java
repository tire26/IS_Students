package com.example.is_students.mai.service;

import com.example.is_students.mai.additionalClasses.UserTableData;
import com.example.is_students.mai.dao.AdministratorPageDAO;
import com.example.is_students.mai.entity.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AdministratorPageService {

    private final AdministratorPageDAO administratorPageDAO = new AdministratorPageDAO();

    public void saveTeacher(User newUser, PhoneNumber phoneNumber, Document document) {
        administratorPageDAO.saveTeacher(newUser, phoneNumber, document);
    }

    public List<Course> getCourses() {
        return administratorPageDAO.getCourses();
    }

    public List<Faculty> getFaculty() {
        return administratorPageDAO.getFaculty();
    }

    public void saveGroup(Group newGroup) {
        administratorPageDAO.saveGroup(newGroup);
    }

    public List<Group> getGroups() {
        return administratorPageDAO.getGroups();
    }

    public List<Status> getStatuses() {
        return administratorPageDAO.getStatuses();
    }

    public void saveStudent(User newUser, PhoneNumber phoneNumber, Document document, Student student, CreditBook newCreditBook) {
        administratorPageDAO.saveStudent(newUser, phoneNumber, document, student, newCreditBook);
    }

    public List<User> getUsers() {
        return administratorPageDAO.getUsers();
    }

    public List<UserTableData> getTeachers() {
        List<User> userList = administratorPageDAO.getTeachers();
        List<UserTableData> tableDataList = new ArrayList<>();
        for (User user : userList) {
            UserTableData tableData = new UserTableData();
            List<Document> document = administratorPageDAO.getDocument(user);
            tableData.setDocument(document.size() == 0 ? null : document.get(0));

            List<PhoneNumber> phoneNumbers = administratorPageDAO.getPhoneNumber(user);
            tableData.setPhoneNumber(phoneNumbers.size() == 0 ? null : phoneNumbers.get(0));
            tableData.setUser(user);
            tableDataList.add(tableData);
        }
        return tableDataList;
    }

    public List<UserTableData> getStudents() {
        List<User> userList = administratorPageDAO.getStudents();
        List<UserTableData> tableDataList = new ArrayList<>();
        for (User user : userList) {
            UserTableData tableData = new UserTableData();

            List<Document> document = administratorPageDAO.getDocument(user);
            tableData.setDocument(document.size() == 0 ? null : document.get(0));

            List<PhoneNumber> phoneNumbers = administratorPageDAO.getPhoneNumber(user);
            tableData.setPhoneNumber(phoneNumbers.size() == 0 ? null : phoneNumbers.get(0));

            tableData.setGroup(administratorPageDAO.getGroup(user));

            tableData.setStudent(administratorPageDAO.getStudent(user));

            tableData.setStatus(administratorPageDAO.getStatus(user));

            tableData.setUser(user);
            tableDataList.add(tableData);
        }
        return tableDataList;
    }

    public List<Lesson> getLessons(Group userData, LocalDate currentDate) {
        return administratorPageDAO.getLessons(userData, currentDate);
    }

    public List<User> getAvailableTeachers(LocalDate currentDate, int currentLesson) {
        return administratorPageDAO.getAvailableTeachers(currentDate, currentLesson);
    }

    public List<Subject> getSubjects() {
        return administratorPageDAO.getSubjects();
    }

    public List<Classroom> getAvailableRooms(LocalDate userData, Integer lessonTime) {
        return administratorPageDAO.getAvailableRooms(userData, lessonTime);
    }

    public void saveOrUpdateLesson(Lesson firstLesson) {
        administratorPageDAO.saveOrUpdateLesson(firstLesson);
    }

    public List<LessonsTime> getLessonsTime() {
        return administratorPageDAO.getLessonsTime();
    }
}
