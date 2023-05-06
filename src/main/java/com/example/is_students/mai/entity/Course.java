package com.example.is_students.mai.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "courses")
@Getter
@Setter
public class Course {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "course_id", nullable = false)
    private Long courseId;

    @Basic
    @Column(name = "course", nullable = true, length = 1)
    private String course;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "course")
    private List<Group> groupList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(courseId, course.courseId) && Objects.equals(this.course, course.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, course);
    }
}
