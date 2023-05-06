package com.example.is_students.mai.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "statuses")
public class Status {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "status_id", nullable = false)
    private Long statusId;
    @Basic
    @Column(name = "status", nullable = true, length = 30)
    private String status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        return Objects.equals(statusId, status.statusId) && Objects.equals(this.status, status.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusId, status);
    }
}
