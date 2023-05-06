package com.example.is_students.mai.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "subject_list", schema = "public", catalog = "postgres")
@Getter
@Setter
public class Subject {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "subject_id", nullable = false)
    private Long subjectId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subject")
    private List<Mark> marks;

    @Basic
    @Column(name = "subject", nullable = true, length = 200)
    private String subject;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject that = (Subject) o;
        return Objects.equals(subjectId, that.subjectId) && Objects.equals(subject, that.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subjectId, subject);
    }
}
