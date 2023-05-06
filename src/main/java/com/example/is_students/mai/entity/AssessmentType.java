package com.example.is_students.mai.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "assessment_types")
@Getter
@Setter
public class AssessmentType {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "assessment_id", nullable = false)
    private Long assessmentTypeId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "assessmentType")
    private List<Mark> marks;

    @Basic
    @Column(name = "assessment", nullable = true, length = 1)
    private String assessment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssessmentType that = (AssessmentType) o;
        return Objects.equals(assessmentTypeId, that.assessmentTypeId) && Objects.equals(assessment, that.assessment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assessmentTypeId, assessment);
    }
}
