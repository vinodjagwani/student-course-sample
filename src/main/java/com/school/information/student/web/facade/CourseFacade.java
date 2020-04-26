package com.school.information.student.web.facade;

import com.querydsl.core.types.Predicate;
import com.school.information.student.web.dto.CourseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseFacade {

    void deleteCourse(final Long id);

    CourseDTO createCourse(final CourseDTO dto);

    CourseDTO findCourseByPredicate(final Predicate predicate);

    CourseDTO updateCourse(final Long id, final CourseDTO dto);

    List<CourseDTO> findAllCoursesByPredicate(final Predicate predicate);

    Page<CourseDTO> findAllCoursesByPredicateAndPage(final Predicate predicate, final Pageable page);
}
