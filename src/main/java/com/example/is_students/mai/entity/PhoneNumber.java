package com.example.is_students.mai.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "phone_numbers", schema = "public", catalog = "postgres")
@Getter
@Setter
public class PhoneNumber {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "phone_number_id", nullable = false)
    private Long phoneNumberId;

    @Basic
    @Column(name = "phone_number", nullable = true, length = 13)
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhoneNumber that)) return false;
        return Objects.equals(getPhoneNumberId(), that.getPhoneNumberId()) && Objects.equals(getPhoneNumber(), that.getPhoneNumber()) && Objects.equals(getUser(), that.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPhoneNumberId(), getPhoneNumber(), getUser());
    }
}
