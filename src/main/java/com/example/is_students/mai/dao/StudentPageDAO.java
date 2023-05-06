package com.example.is_students.mai.dao;

import com.example.is_students.mai.HibernateSessionFactoryUtil;
import com.example.is_students.mai.entity.*;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import java.time.LocalDate;
import java.util.List;

public class StudentPageDAO {

    public List<Mark> findMarksById(User user) {
        Session session = HibernateSessionFactoryUtil.buildSessionFactory().openSession();
        List<Mark> marks;
        NativeQuery<Mark> query = session.createNativeQuery(
                """
                        SELECT * FROM marks
                        INNER JOIN users as u ON u.userlogin = :login and u.userpassword = :password
                        INNER JOIN students as s ON s.user_id = u.user_id
                        INNER JOIN credit_books as cb ON cb.student_id = s.student_id
                        WHERE marks.credit_book_id = cb.credit_book_id""", Mark.class);
        query.setParameter("login", user.getLogin());
        query.setParameter("password", user.getPassword());
        marks = query.getResultList();
        session.close();
        return marks;
    }


    public static List<Lesson> getLessons(User user, LocalDate currentDate) {
        Session session = HibernateSessionFactoryUtil.buildSessionFactory().openSession();
        List<Lesson> lessons;
        NativeQuery<Lesson> query = session.createNativeQuery("""
                SELECT * FROM lessons as l
                INNER JOIN groups g on l.group_id = g.group_id
                INNER JOIN students s on g.group_id = s.group_id
                where s.user_id = :student 
                AND extract(DAY FROM l.date) = :currentDay
                 AND extract(MONTH FROM l.date) = :currentMonth 
                 AND extract(YEAR FROM l.date) = :currentYear
                """, Lesson.class);
        query.setParameter("student", user.getUserId());
        query.setParameter("currentDay", currentDate.getDayOfMonth());
        query.setParameter("currentMonth", currentDate.getMonth().getValue());
        query.setParameter("currentYear", currentDate.getYear());
        lessons = query.getResultList();
        session.close();
        return lessons;
    }

    public Group getGroup(User user) {
        Session session = HibernateSessionFactoryUtil.buildSessionFactory().openSession();
        Group group;
        NativeQuery<Group> query = session.createNativeQuery("""
                SELECT * FROM groups as g
                INNER JOIN students s on g.group_id = s.group_id
                INNER JOIN users u on s.user_id = u.user_id
                WHERE  u.user_id = :student
                """, Group.class);
        query.setParameter("student", user.getUserId());
        group = query.getSingleResult();
        session.close();
        return group;
    }

    public List<PhoneNumber> getPhoneNumber(User user) {
        Session session = HibernateSessionFactoryUtil.buildSessionFactory().openSession();
        List<PhoneNumber> phoneNumbers;
        NativeQuery<PhoneNumber> query = session.createNativeQuery("""
                SELECT * FROM phone_numbers as pn
                WHERE pn.user_id =:student
                """, PhoneNumber.class);
        query.setParameter("student", user.getUserId());
        phoneNumbers = query.getResultList();
        session.close();
        return phoneNumbers;
    }

    public List<Document> getDocuments(User user) {
        Session session = HibernateSessionFactoryUtil.buildSessionFactory().openSession();
        List<Document> documentList;
        NativeQuery<Document> query = session.createNativeQuery("""
                SELECT * FROM documents as d
                WHERE d.user_id =:student
                """, Document.class);
        query.setParameter("student", user.getUserId());
        documentList = query.getResultList();
        session.close();
        return documentList;
    }

    public CreditBook getCreditBook(User user) {
        Session session = HibernateSessionFactoryUtil.buildSessionFactory().openSession();
        CreditBook creditBook;
        NativeQuery<CreditBook> query = session.createNativeQuery("""
                SELECT * FROM credit_books as cb
                INNER JOIN students s on cb.student_id = s.student_id
                INNER JOIN users u on s.user_id = u.user_id
                WHERE  u.user_id = :student
                """, CreditBook.class);
        query.setParameter("student", user.getUserId());
        creditBook = query.getSingleResult();
        session.close();
        return creditBook;
    }

    public Faculty getFaculty(User user) {
        Session session = HibernateSessionFactoryUtil.buildSessionFactory().openSession();
        Faculty faculty;
        NativeQuery<Faculty> query = session.createNativeQuery("""
                SELECT * FROM faculties as f
                INNER JOIN groups g on f.faculty_id = g.faculty_id
                INNER JOIN students s on s.group_id = g.group_id
                INNER JOIN users u on s.user_id = u.user_id
                WHERE  u.user_id = :student
                """, Faculty.class);
        query.setParameter("student", user.getUserId());
        faculty = query.getSingleResult();
        session.close();
        return faculty;
    }

    public Course getCourse(User user) {
        Session session = HibernateSessionFactoryUtil.buildSessionFactory().openSession();
        Course course;
        NativeQuery<Course> query = session.createNativeQuery("""
                SELECT * FROM courses as c
                INNER JOIN groups g on c.course_id = g.course_id
                INNER JOIN students s on g.group_id = s.group_id
                INNER JOIN users u on s.user_id = u.user_id
                WHERE  u.user_id = :student
                """, Course.class);
        query.setParameter("student", user.getUserId());
        course = query.getSingleResult();
        session.close();
        return course;
    }

    public Status getStatus(User user) {
        Session session = HibernateSessionFactoryUtil.buildSessionFactory().openSession();
        Status status;
        NativeQuery<Status> query = session.createNativeQuery("""
                SELECT * FROM statuses as s
                INNER JOIN students st on s.status_id = st.student_status_id
                INNER JOIN users u on st.user_id = u.user_id
                WHERE  u.user_id = :student
                """, Status.class);
        query.setParameter("student", user.getUserId());
        status = query.getSingleResult();
        session.close();
        return status;
    }
}
