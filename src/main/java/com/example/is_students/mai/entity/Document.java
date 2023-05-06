package com.example.is_students.mai.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Setter
@Getter
@Table(name = "documents")
public class Document {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "document_id", nullable = false)
    private Long documentId;

    @Basic
    @Column(name = "document_number", nullable = true, length = 100)
    private String documentNumber;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Document document)) return false;
        return Objects.equals(getDocumentId(), document.getDocumentId()) && Objects.equals(getDocumentNumber(), document.getDocumentNumber()) && Objects.equals(getUser(), document.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDocumentId(), getDocumentNumber(), getUser());
    }
}
