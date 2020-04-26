package com.school.information.student.web.facade;

import com.querydsl.core.types.Predicate;
import com.school.information.student.web.dto.StudentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface StudentFacade {

    void deleteStudent(final Long id);

    StudentDTO createStudent(final StudentDTO dto);

    StudentDTO findByPredicate(final Predicate predicate);

    StudentDTO updateStudent(final Long id, final StudentDTO dto);

    Collection<StudentDTO> findAllByPredicate(final Predicate predicate);

    Page<StudentDTO> findAllByPredicateAndPage(final Predicate predicate, final Pageable page);
}
