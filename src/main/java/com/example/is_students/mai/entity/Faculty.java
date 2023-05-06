package com.example.is_students.mai.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "faculties")
@Getter
@Setter
public class Faculty {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "faculty_id", nullable = false)
    private Long facultyId;
    @Basic
    @Column(name = "faculty", nullable = true, length = 200)
    private String faculty;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "faculty")
    private List<Group> groups;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return Objects.equals(facultyId, faculty.facultyId) && Objects.equals(this.faculty, faculty.faculty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(facultyId, faculty);
    }
}
