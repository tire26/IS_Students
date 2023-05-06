package com.example.is_students.mai.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "credit_books", schema = "public", catalog = "postgres")
@Getter
@Setter
public class CreditBook {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "credit_book_id", nullable = false)
    private Long creditBookId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    private Student student;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "creditBook")
    private List<Mark> marks;

    @Basic
    @Column(name = "credit_book_number", nullable = true, length = 100)
    private String creditBookNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditBook that = (CreditBook) o;
        return Objects.equals(creditBookId, that.creditBookId) && Objects.equals(creditBookNumber, that.creditBookNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(creditBookId, creditBookNumber);
    }
}
