package com.school.information.student.web.dto;


import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressDTO implements Serializable {

    String homeAddress;

    String apartmentNo;

    String city;

    String state;

    String zipCode;

    String country;
}
