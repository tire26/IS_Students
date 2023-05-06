package com.example.is_students.mai.dao;

import com.example.is_students.mai.HibernateSessionFactoryUtil;
import com.example.is_students.mai.entity.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.time.LocalDate;
import java.util.List;

public class AdministratorPageDAO {
    public void saveTeacher(User newUser, PhoneNumber phoneNumber, Document document) {
        Session session = HibernateSessionFactoryUtil.buildSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();

        session.saveOrUpdate(newUser);

        document.setUser(newUser);
        session.saveOrUpdate(document);


        phoneNumber.setUser(newUser);
        session.saveOrUpdate(phoneNumber);


        tx1.commit();
        session.close();
    }

    public List<Course> getCourses() {
        Session session = HibernateSessionFactoryUtil.buildSessionFactory().openSession();
        List<Course> course;
        NativeQuery<Course> query = session.createNativeQuery("""
                SELECT * FROM courses as c
                """, Course.class);
        course = query.getResultList();
        session.close();
        return course;
    }

    public List<Faculty> getFaculty() {
        Session session = HibernateSessionFactoryUtil.buildSessionFactory().openSession();
        List<Faculty> facultyList;
        NativeQuery<Faculty> query = session.createNativeQuery("""
                SELECT * FROM faculties as f
                """, Faculty.class);
        facultyList = query.getResultList();
        session.close();
        return facultyList;
    }

    public void saveGroup(Group newGroup) {
        Session session = HibernateSessionFactoryUtil.buildSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.saveOrUpdate(newGroup);
        tx1.commit();
        session.close();
    }

    public List<Group> getGroups() {
        Session session = HibernateSessionFactoryUtil.buildSessionFactory().openSession();
        List<Group> groupList;
        NativeQuery<Group> query = session.createNativeQuery("""
                SELECT * FROM groups as g
                """, Group.class);
        groupList = query.getResultList();
        session.close();
        return groupList;
    }

    public List<Status> getStatuses() {
        Session session = HibernateSessionFactoryUtil.buildSessionFactory().openSession();
        List<Status> statusList;
        NativeQuery<Status> query = session.createNativeQuery("""
                SELECT * FROM statuses as s
                """, Status.class);
        statusList = query.getResultList();
        session.close();
        return statusList;
    }

    public void saveStudent(User newUser, PhoneNumber phoneNumber, Document document, Student student, CreditBook newCreditBook) {
        Session session = HibernateSessionFactoryUtil.buildSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();

        session.saveOrUpdate(newUser);

        document.setUser(newUser);
        session.saveOrUpdate(document);


        phoneNumber.setUser(newUser);
        session.saveOrUpdate(phoneNumber);

        student.setUser(newUser);
        session.saveOrUpdate(student);

        newCreditBook.setStudent(student);
        session.saveOrUpdate(newCreditBook);

        tx1.commit();
        session.close();
    }

    public List<User> getUsers() {
        Session session = HibernateSessionFactoryUtil.buildSessionFactory().openSession();
        List<User> userList;
        NativeQuery<User> query = session.createNativeQuery("""
                SELECT * FROM users as u
                """, User.class);
        userList = query.getResultList();
        session.close();
        return userList;
    }

    public List<User> getTeachers() {
        Session session = HibernateSessionFactoryUtil.buildSessionFactory().openSession();
        List<User> userList;
        NativeQuery<User> query = session.createNativeQuery("""
                SELECT * FROM users as u
                INNER JOIN user_types ut on ut.user_type_id = u.user_type_id
                WHERE ut.user_type = 'преподаватель'
                """, User.class);
        userList = query.getResultList();
        session.close();
        return userList;
    }

    public List<Document> getDocument(User user) {
        Session session = HibernateSessionFactoryUtil.buildSessionFactory().openSession();
        List<Document> document;
        NativeQuery<Document> query = session.createNativeQuery("""
                SELECT * FROM documents as d
                WHERE d.user_id = :userId
                """, Document.class);
        query.setParameter("userId", user.getUserId());
        document = query.getResultList();
        session.close();
        return document;
    }

    public List<PhoneNumber> getPhoneNumber(User user) {
        Session session = HibernateSessionFactoryUtil.buildSessionFactory().openSession();
        List<PhoneNumber> phoneNumber;
        NativeQuery<PhoneNumber> query = session.createNativeQuery("""
                SELECT * FROM phone_numbers as pn
                WHERE pn.user_id = :userId
                """, PhoneNumber.class);
        query.setParameter("userId", user.getUserId());
        phoneNumber = query.getResultList();
        session.close();
        return phoneNumber;
    }

    public Group getGroup(User user) {
        Session session = HibernateSessionFactoryUtil.buildSessionFactory().openSession();
        Group group;
        NativeQuery<Group> query = session.createNativeQuery("""
                SELECT * FROM groups as g
                INNER JOIN students s on g.group_id = s.group_id
                WHERE s.user_id = :userId
                """, Group.class);
        query.setParameter("userId", user.getUserId());
        group = query.getSingleResult();
        session.close();
        return group;
    }

    public Student getStudent(User user) {
        Session session = HibernateSessionFactoryUtil.buildSessionFactory().openSession();
        Student student;
        NativeQuery<Student> query = session.createNativeQuery("""
                SELECT * FROM students as s
                WHERE s.user_id = :userId
                """, Student.class);
        query.setParameter("userId", user.getUserId());
        student = query.getSingleResult();
        session.close();
        return student;
    }

    public Status getStatus(User user) {
        Session session = HibernateSessionFactoryUtil.buildSessionFactory().openSession();
        Status status;
        NativeQuery<Status> query = session.createNativeQuery("""
                SELECT * FROM statuses as s
                INNER JOIN students s2 on s.status_id = s2.student_status_id
                WHERE s2.user_id = :userId
                """, Status.class);
        query.setParameter("userId", user.getUserId());
        status = query.getSingleResult();
        session.close();
        return status;
    }

    public List<User> getStudents() {
        Session session = HibernateSessionFactoryUtil.buildSessionFactory().openSession();
        List<User> userList;
        NativeQuery<User> query = session.createNativeQuery("""
                SELECT * FROM users as u
                INNER JOIN user_types ut on ut.user_type_id = u.user_type_id
                WHERE ut.user_type = 'студент'
                """, User.class);
        userList = query.getResultList();
        session.close();
        return userList;
    }

    public List<Lesson> getLessons(Group userData, LocalDate currentDate) {
        Session session = HibernateSessionFactoryUtil.buildSessionFactory().openSession();
        List<Lesson> lessonList;
        NativeQuery<Lesson> query = session.createNativeQuery("""
                SELECT * FROM lessons as l
                WHERE l.group_id = :groupId 
                 AND extract(DAY FROM l.date) = :currentDay
                 AND extract(MONTH FROM l.date) = :currentMonth 
                 AND extract(YEAR FROM l.date) = :currentYear
                """, Lesson.class);
        query.setParameter("groupId", userData.getGroupId());
        query.setParameter("currentDay", currentDate.getDayOfMonth());
        query.setParameter("currentMonth", currentDate.getMonth().getValue());
        query.setParameter("currentYear", currentDate.getYear());
        lessonList = query.getResultList();
        session.close();
        return lessonList;
    }

    public List<User> getAvailableTeachers(LocalDate currentDate, int currentLesson) {
        Session session = HibernateSessionFactoryUtil.buildSessionFactory().openSession();
        List<User> userList;
        NativeQuery<User> query = session.createNativeQuery("""
                SELECT DISTINCT(u.user_id), u.user_type_id, u.username, u.userfullname, u.usersurname, u.userlogin, u.userpassword FROM users as u
                                INNER JOIN user_types ut on u.user_type_id = ut.user_type_id
                                LEFT JOIN lessons l on u.user_id = l.teacher_id
                				AND (
                				(extract(DAY FROM l.date) != :currentDay OR extract(MONTH FROM l.date) != :currentMonth OR extract(YEAR FROM l.date) != :currentYear)
                				 OR 
                				 (l.lesson_id != :currentLesson AND (extract(DAY FROM l.date) != :currentDay OR extract(MONTH FROM l.date) != :currentMonth OR extract(YEAR FROM l.date) != :currentYear))
                				 )
                				WHERE  ut.user_type = 'преподаватель'
                """, User.class);
        query.setParameter("currentLesson", currentLesson);
        query.setParameter("currentDay", currentDate.getDayOfMonth());
        query.setParameter("currentMonth", currentDate.getMonth().getValue());
        query.setParameter("currentYear", currentDate.getYear());
        userList = query.getResultList();
        session.close();
        return userList;
    }

    public List<Subject> getSubjects() {
        Session session = HibernateSessionFactoryUtil.buildSessionFactory().openSession();
        List<Subject> subjectList;
        NativeQuery<Subject> query = session.createNativeQuery("""
                SELECT * FROM subject_list as s
                """, Subject.class);
        subjectList = query.getResultList();
        session.close();
        return subjectList;
    }

    public List<Classroom> getAvailableRooms(LocalDate userData, Integer lesson) {
        Session session = HibernateSessionFactoryUtil.buildSessionFactory().openSession();
        List<Classroom> userList;
        NativeQuery<Classroom> query = session.createNativeQuery("""
                SELECT * FROM classrooms as c
                                                LEFT JOIN lessons l on c.classroom_id = l.classroom_id
                                				AND (
                                				(extract(DAY FROM l.date) != :currentDay OR extract(MONTH FROM l.date) != :currentMonth OR extract(YEAR FROM l.date) != :currentYear)
                                				 OR
                                				 (l.lesson_number != :currentLesson AND (extract(DAY FROM l.date) != :currentDay OR extract(MONTH FROM l.date) != :currentMonth OR extract(YEAR FROM l.date) != :currentYear))
                                				 )
                """, Classroom.class);
        query.setParameter("currentLesson", lesson);
        query.setParameter("currentDay", userData.getDayOfMonth());
        query.setParameter("currentMonth", userData.getMonth().getValue());
        query.setParameter("currentYear", userData.getYear());
        userList = query.getResultList();
        session.close();
        return userList;
    }

    public void saveOrUpdateLesson(Lesson firstLesson) {
        Session session = HibernateSessionFactoryUtil.buildSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();

        session.saveOrUpdate(firstLesson);

        tx1.commit();
        session.close();
    }

    public List<LessonsTime> getLessonsTime() {
        Session session = HibernateSessionFactoryUtil.buildSessionFactory().openSession();
        List<LessonsTime> lessonsTimeList;
        NativeQuery<LessonsTime> query = session.createNativeQuery("""
                SELECT * FROM lessons_time as l
              
                """, LessonsTime.class);
        lessonsTimeList = query.getResultList();
        session.close();
        return lessonsTimeList;
    }
}
