package com.example.is_students.mai.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "marks", schema = "public", catalog = "postgres")
public class Mark {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assessment_type_id")
    private AssessmentType assessmentType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_type_id")
    private Subject subject;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "credit_book_id")
    private CreditBook creditBook;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ref_mark_id")
    private RefMark mark;

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAssessmentType(), getSubject(), getCreditBook(), getMark());
    }
}
