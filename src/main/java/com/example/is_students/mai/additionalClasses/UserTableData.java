package com.example.is_students.mai.additionalClasses;

import com.example.is_students.mai.entity.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTableData {

    private CreditBook creditBook;
    private User user;
    private Student student;
    private PhoneNumber phoneNumber;
    private Document document;
    private Group group;
    private Status status;
}
