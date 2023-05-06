package com.example.is_students.mai.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "classrooms")
@Getter
@Setter
public class Classroom {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "classroom_id", nullable = false)
    private Long classroomId;

    @Basic
    @Column(name = "classroom", nullable = true, length = 4)
    private String classroom;

    @OneToMany(mappedBy = "classroom", fetch = FetchType.LAZY)
    List<Lesson> lessonList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Classroom that = (Classroom) o;
        return Objects.equals(classroomId, that.classroomId) && Objects.equals(classroom, that.classroom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classroomId, classroom);
    }
}
