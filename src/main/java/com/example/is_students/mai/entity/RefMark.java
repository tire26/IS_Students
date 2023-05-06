package com.example.is_students.mai.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ref_marks")
@Getter
@Setter
public class RefMark {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ref_mark_id", nullable = false)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mark")
    private List<Mark> marks;

    @Basic
    @Column(name = "mark", nullable = false, length = 10)
    private String mark;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RefMark refMark = (RefMark) o;
        return Objects.equals(id, refMark.id) && Objects.equals(mark, refMark.mark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mark);
    }
}
