package com.school.information.exception.constant;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class DbConstraintMapping {

    public static Map<String, String> getConstraintByName() {
        Map<String, String> dbConstraint = new HashMap<>();
        dbConstraint.put("UNQ_PER_INFO_SSN", "SSN can't be duplicate");
        return dbConstraint;
    }

}
