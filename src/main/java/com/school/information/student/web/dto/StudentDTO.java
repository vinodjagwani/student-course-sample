package com.school.information.student.web.dto;

import com.school.information.student.repository.entity.Gender;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentDTO implements Serializable {

    Long id;

    String firstName;

    String middleName;

    String lastName;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    String dob;

    Gender gender;

    AddressDTO address;

    String phoneNumber;

    String email;

    String registrationNumber;

    List<String> courseNames = new ArrayList<>(0);

    List<CourseDTO> courses = new ArrayList<>(0);
}
