package com.example.is_students.mai.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_types", schema = "public", catalog = "postgres")
public class UserType implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_type_id", nullable = false)
    private Long userTypeId;

    @Column(name = "user_type", nullable = true, length = 30)
    private String userType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserType userTypes = (UserType) o;
        return Objects.equals(userTypeId, userTypes.userTypeId) && Objects.equals(userType, userTypes.userType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userTypeId, userType);
    }
}
