package com.example.is_students.mai.service;

import com.example.is_students.mai.additionalClasses.TableData;
import com.example.is_students.mai.dao.TeacherPageDAO;
import com.example.is_students.mai.entity.*;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TeacherPageService {

    private final TeacherPageDAO teacherPageDAO = new TeacherPageDAO();

    public TeacherPageService() {

    }

    public void setMarks(List<Mark> marks) {
        teacherPageDAO.setMarks(marks);
    }

    public List<Subject> getSubjects(User user) {
        return teacherPageDAO.getSubjects(user);
    }

    public List<Group> getGroups(User user, Subject subject) {

        return teacherPageDAO.getGroups(user, subject);
    }

    public List<TableData> getCreditBooks(Group group, Subject subject) {
        List<Student> studentList = teacherPageDAO.getStudents(group, subject);
        List<TableData> tableDataList = new ArrayList<>(studentList.size());
        TableData currTableData;
        Student stud;
        for (int i = 0; i < studentList.size(); i++) {
            stud = studentList.get(i);
            List<CreditBook> creditBookList = teacherPageDAO.getCreditBook(stud);
            List<Mark> markList =  teacherPageDAO.getMarks(stud, subject);
            currTableData = new TableData();
            Mark mark = new Mark();

            if (markList.size() > 0) {
                mark = markList.get(0);
                mark.setMark(mark.getMark());
            }
            mark.setSubject(subject);
            if (creditBookList.size() > 0) {
                mark.setCreditBook(creditBookList.get(0));
            }


            currTableData.setMarkString(new SimpleStringProperty(""));
            currTableData.setMarkTableData(mark);
            currTableData.setCreditBookString(new SimpleStringProperty(mark.getCreditBook().getCreditBookNumber()));
            tableDataList.add(i, currTableData);
        }
        return tableDataList;
    }

    public List<RefMark> getRefMarks() {
        return teacherPageDAO.getRefMarks();
    }

    public void saveMark(Mark studentMark, RefMark refMark) {
        teacherPageDAO.saveMark(studentMark, refMark);
    }

    public List<Lesson> getLessons(User user, LocalDate currentDate) {
        List<Lesson> lessonList;
        lessonList = teacherPageDAO.getLessons(user, currentDate);
        return lessonList;
    }

    public List<AssessmentType> getAssessmentTypes() {
        return teacherPageDAO.getAssessmentTypes();
    }

}
