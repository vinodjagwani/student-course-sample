package com.school.information.student.repository;

import com.school.information.student.repository.entity.CourseEntity;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CourseRepository extends PagingAndSortingRepository<CourseEntity, Long>, QuerydslPredicateExecutor<CourseEntity> {
}
