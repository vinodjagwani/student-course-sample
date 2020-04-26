package com.school.information.student.service;

import com.querydsl.core.types.Predicate;
import com.school.information.student.repository.entity.StudentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface StudentService {


    void delete(final Long id);

    StudentEntity createOrUpdate(final StudentEntity entity);

    StudentEntity findOne(final Predicate predicate);

    Collection<StudentEntity> findAll(final Predicate predicate);

    Page<StudentEntity> findAll(final Predicate predicate, final Pageable page);
}
