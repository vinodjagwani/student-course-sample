package com.school.information.student.repository.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;


@Data
@Entity
@Table(name = "address")
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressEntity extends AbstractEntity<String> implements Serializable {

    @Column(length = 50)
    String homeAddress;

    @Column(length = 25)
    String apartmentNo;

    @Column(length = 25)
    String city;

    @Column(length = 25)
    String state;

    @Column(length = 25)
    String zipCode;

    @Column(length = 25)
    String country;

}
