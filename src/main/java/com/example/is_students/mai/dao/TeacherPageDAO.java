package com.example.is_students.mai.dao;

import com.example.is_students.mai.HibernateSessionFactoryUtil;
import com.example.is_students.mai.entity.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.time.LocalDate;
import java.util.List;

public class TeacherPageDAO {

    public void setMarks(List<Mark> marks) {
        Session session = HibernateSessionFactoryUtil.buildSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        for (Mark mark : marks) {
            mark.setId(null);
            session.persist(mark);
        }
        tx1.commit();
        session.close();
    }

    public List<Subject> getSubjects(User user) {
        Session session = HibernateSessionFactoryUtil.buildSessionFactory().openSession();
        List<Subject> subjectList;
        NativeQuery<Subject> query = session.createNativeQuery("""
                SELECT DISTINCT ON (subject_list.subject_id) subject_list.subject_id, subject_list.subject FROM subject_list
                INNER JOIN lessons l on subject_list.subject_id = l.subject_id
                WHERE l.teacher_id = :user_id
                """, Subject.class);
        query.setParameter("user_id", user.getUserId());
        subjectList = query.getResultList();
        session.close();
        return subjectList;
    }

    public List<Student> getStudents(Group group, Subject subject) {
        Session session = HibernateSessionFactoryUtil.buildSessionFactory().openSession();
        List<Student> studentList;
        NativeQuery<Student> query = session.createNativeQuery("""
                SELECT * FROM students
                                INNER JOIN credit_books cb on cb.student_id = students.student_id
                				INNER JOIN groups g on g.group_id = students.group_id AND g.group_id = :group
                                LEFT JOIN marks m on cb.credit_book_id = m.credit_book_id AND m.subject_type_id = :subject
                """, Student.class);
        query.setParameter("group", group.getGroupId());
        query.setParameter("subject", subject.getSubjectId());
        studentList = query.getResultList();
        session.close();
        return studentList;
    }

    public List<Group> getGroups(User user, Subject subject) {
        Session session = HibernateSessionFactoryUtil.buildSessionFactory().openSession();
        List<Group> groupList;
        NativeQuery<Group> query = session.createNativeQuery("""
                SELECT DISTINCT ON (g.group_id) g.group_id, g.course_id, g.faculty_id, g.group_number FROM groups AS g
                INNER JOIN lessons l on g.group_id = l.group_id AND l.teacher_id = :teacher AND l.subject_id = :subject
                """, Group.class);
        query.setParameter("teacher", user.getUserId());
        query.setParameter("subject", subject.getSubjectId());
        groupList = query.getResultList();
        session.close();
        return groupList;
    }

    public List<RefMark> getRefMarks() {
        Session session = HibernateSessionFactoryUtil.buildSessionFactory().openSession();
        List<RefMark> creditBookList;
        NativeQuery<RefMark> query = session.createNativeQuery("""
                SELECT * FROM ref_marks
                """, RefMark.class);
        creditBookList = query.getResultList();
        session.close();
        return creditBookList;
    }

    public void saveMark(Mark studentMark, RefMark refMark) {
        Session session = HibernateSessionFactoryUtil.buildSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        studentMark.setMark(refMark);
        session.saveOrUpdate(studentMark);
        tx1.commit();
        session.close();
    }

    public List<Lesson> getLessons(User user, LocalDate currentDate) {
        Session session = HibernateSessionFactoryUtil.buildSessionFactory().openSession();
        List<Lesson> lessons;
        NativeQuery<Lesson> query = session.createNativeQuery("""
                SELECT * FROM lessons as l
                where teacher_id = :teacher 
                AND extract(DAY FROM l.date) = :currentDay
                 AND extract(MONTH FROM l.date) = :currentMonth 
                 AND extract(YEAR FROM l.date) = :currentYear
                """, Lesson.class);
        query.setParameter("teacher", user.getUserId());
        query.setParameter("currentDay", currentDate.getDayOfMonth());
        query.setParameter("currentMonth", currentDate.getMonth().getValue());
        query.setParameter("currentYear", currentDate.getYear());
        lessons = query.getResultList();
        session.close();
        return lessons;
    }

    public List<CreditBook> getCreditBook(Student stud) {
        Session session = HibernateSessionFactoryUtil.buildSessionFactory().openSession();
        List<CreditBook> creditBook;
        NativeQuery<CreditBook> query = session.createNativeQuery("""
                SELECT * FROM credit_books as cb
                where cb.student_id = :stud 
                """, CreditBook.class);
        query.setParameter("stud", stud.getStudentId());

        creditBook = query.getResultList();
        session.close();
        return creditBook;
    }

    public List<Mark> getMarks(Student stud, Subject subject) {
        Session session = HibernateSessionFactoryUtil.buildSessionFactory().openSession();
        List<Mark> creditBook;
        NativeQuery<Mark> query = session.createNativeQuery("""
                SELECT * FROM marks as m
                INNER JOIN credit_books cb on cb.student_id = :stud
                INNER JOIN subject_list sl on sl.subject_id = :subject
                where m.credit_book_id = cb.credit_book_id
                """, Mark.class);
        query.setParameter("stud", stud.getStudentId());
        query.setParameter("subject", subject.getSubjectId());
        creditBook = query.getResultList();
        session.close();
        return creditBook;
    }

    public List<AssessmentType> getAssessmentTypes() {
        Session session = HibernateSessionFactoryUtil.buildSessionFactory().openSession();
        List<AssessmentType> assessmentTypeList;
        NativeQuery<AssessmentType> query = session.createNativeQuery("""
                SELECT * FROM assessment_types
                """, AssessmentType.class);
        assessmentTypeList = query.getResultList();
        session.close();
        return assessmentTypeList;
    }
}
