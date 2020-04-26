package com.school.information.student.service;

import com.querydsl.core.types.Predicate;
import com.school.information.student.repository.entity.CourseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface CourseService {

    void delete(final Long id);

    CourseEntity findOne(final Predicate predicate);

    CourseEntity createOrUpdate(final CourseEntity entity);

    Collection<CourseEntity> findAll(final Predicate predicate);

    Page<CourseEntity> findAll(final Predicate predicate, final Pageable page);
}
