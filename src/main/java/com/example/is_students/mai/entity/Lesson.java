package com.example.is_students.mai.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "lessons")
public class Lesson {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "lesson_id", nullable = false)
    private Long lessonId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    private User user;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_number")
    private LessonsTime lessonsTime;

    @Basic
    @Column(name = "date", nullable = false)
    private Date time;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lesson lesson)) return false;
        return Objects.equals(getLessonId(), lesson.getLessonId()) && Objects.equals(getGroup(), lesson.getGroup()) && Objects.equals(getUser(), lesson.getUser()) && Objects.equals(getSubject(), lesson.getSubject()) && Objects.equals(getTime(), lesson.getTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLessonId(), getGroup(), getUser(), getSubject(), getTime());
    }
}
