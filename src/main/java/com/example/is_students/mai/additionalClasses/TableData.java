package com.example.is_students.mai.additionalClasses;

import com.example.is_students.mai.entity.Mark;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableData {

    private StringProperty markString;
    private StringProperty creditBookString;
    private Mark markTableData;
}