package com.example.is_students.mai.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "lessons_time", schema = "public", catalog = "postgres")
@Getter
@Setter
public class LessonsTime {
    @Basic
    @Column(name = "time", nullable = true)
    private Time time;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LessonsTime that = (LessonsTime) o;
        return Objects.equals(time, that.time) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, id);
    }
}
