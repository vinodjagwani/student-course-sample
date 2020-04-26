package com.school.information.student.web.facade.impl;

import com.querydsl.core.types.Predicate;
import com.school.information.student.repository.entity.CourseEntity;
import com.school.information.student.repository.entity.QCourseEntity;
import com.school.information.student.service.impl.CourseService;
import com.school.information.student.web.dto.CourseDTO;
import com.school.information.student.web.facade.CourseFacade;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.school.information.student.web.constant.Constant.COURSE_INFO_D_T_E;
import static com.school.information.student.web.constant.Constant.COURSE_INFO_E_T_D;


@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CourseFacadeIml implements CourseFacade {

    Mapper dozerMapper;

    CourseService courseService;

    @Override
    public void deleteCourse(final Long id) {
        courseService.delete(id);
    }

    @Override
    public CourseDTO createCourse(final CourseDTO dto) {
        return dozerMapper.map(courseService.createOrUpdate(dozerMapper.map(dto, CourseEntity.class, COURSE_INFO_D_T_E)), CourseDTO.class, COURSE_INFO_E_T_D);
    }

    @Override
    public CourseDTO findCourseByPredicate(final Predicate predicate) {
        return dozerMapper.map(courseService.findOne(predicate), CourseDTO.class, COURSE_INFO_E_T_D);
    }

    @Override
    public CourseDTO updateCourse(final Long id, final CourseDTO dto) {
        CourseEntity entity = courseService.findOne(QCourseEntity.courseEntity.id.eq(id));
        dozerMapper.map(dto, entity, COURSE_INFO_D_T_E);
        entity = courseService.createOrUpdate(entity);
        return dozerMapper.map(entity, CourseDTO.class, COURSE_INFO_E_T_D);
    }

    @Override
    public List<CourseDTO> findAllCoursesByPredicate(final Predicate predicate) {
        return courseService.findAll(predicate).stream().map(s ->
                dozerMapper.map(s, CourseDTO.class, COURSE_INFO_E_T_D)).collect(Collectors.toList());
    }

    @Override
    public Page<CourseDTO> findAllCoursesByPredicateAndPage(final Predicate predicate, final Pageable page) {
        return courseService.findAll(predicate, page).map(this::convert);
    }

    private CourseDTO convert(final CourseEntity entity) {
        return dozerMapper.map(entity, CourseDTO.class, COURSE_INFO_E_T_D);
    }
}
