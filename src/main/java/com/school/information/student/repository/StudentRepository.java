package com.school.information.student.repository;


import com.school.information.student.repository.entity.StudentEntity;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StudentRepository extends PagingAndSortingRepository<StudentEntity, Long>, QuerydslPredicateExecutor<StudentEntity> {


}
