package com.example.is_students.mai.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users", schema = "public", catalog = "postgres")
public class User implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "user_type_id", nullable = false)
    private Long userTypeId;

    @Column(name = "userlogin", nullable = true, length = 200)
    private String login;

    @Column(name = "userpassword", nullable = true, length = 200)
    private String password;

    @Column(name = "username", nullable = true, length = -1)
    private String name;

    @Column(name = "userfullname", nullable = true, length = -1)
    private String fullname;

    @Column(name = "usersurname", nullable = true, length = -1)
    private String surname;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) && Objects.equals(userTypeId, user.userTypeId) && Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(name, user.name) && Objects.equals(fullname, user.fullname) && Objects.equals(surname, user.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userTypeId, login, password, name, fullname, surname);
    }
}
