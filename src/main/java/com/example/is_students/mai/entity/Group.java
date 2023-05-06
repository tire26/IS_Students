package com.example.is_students.mai.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "groups")
public class Group {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    private Course course;

    @Basic
    @Column(name = "group_number", nullable = false, length = 100)
    private String groupNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group group)) return false;
        return Objects.equals(getGroupId(), group.getGroupId()) && Objects.equals(getFaculty(), group.getFaculty()) && Objects.equals(getCourse(), group.getCourse()) && Objects.equals(getGroupNumber(), group.getGroupNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGroupId(), getFaculty(), getCourse(), getGroupNumber());
    }
}
