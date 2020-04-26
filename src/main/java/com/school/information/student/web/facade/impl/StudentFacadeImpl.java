package com.school.information.student.web.facade.impl;

import com.querydsl.core.types.Predicate;
import com.school.information.student.repository.entity.CourseEntity;
import com.school.information.student.repository.entity.QCourseEntity;
import com.school.information.student.repository.entity.QStudentEntity;
import com.school.information.student.repository.entity.StudentEntity;
import com.school.information.student.service.CourseService;
import com.school.information.student.service.StudentService;
import com.school.information.student.web.dto.StudentDTO;
import com.school.information.student.web.facade.StudentFacade;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.school.information.student.web.constant.Constant.STUDENT_INFO_D_T_E;
import static com.school.information.student.web.constant.Constant.STUDENT_INFO_E_T_D;


@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class StudentFacadeImpl implements StudentFacade {


    Mapper dozerMapper;

    CourseService courseService;

    StudentService studentService;

    @Override
    public void deleteStudent(final Long id) {
        studentService.delete(id);
    }

    @Override
    public StudentDTO createStudent(final StudentDTO dto) {
        final Long[] ids = dto.getCourseNames().stream().map(Long::parseLong).toArray(Long[]::new);
        final List<CourseEntity> courses = (List<CourseEntity>) courseService.findAll(QCourseEntity.courseEntity.id.in(ids));
        final StudentEntity entity = dozerMapper.map(dto, StudentEntity.class, STUDENT_INFO_D_T_E);
        entity.setCourses(courses);
        return dozerMapper.map(studentService.createOrUpdate(entity), StudentDTO.class, STUDENT_INFO_E_T_D);
    }

    @Override
    public StudentDTO updateStudent(final Long id, final StudentDTO dto) {
        final Long[] ids = dto.getCourseNames().stream().map(Long::parseLong).toArray(Long[]::new);
        final List<CourseEntity> courses = (List<CourseEntity>) courseService.findAll(QCourseEntity.courseEntity.id.in(ids));
        StudentEntity entity = studentService.findOne(QStudentEntity.studentEntity.id.eq(id));
        dozerMapper.map(dto, entity, STUDENT_INFO_D_T_E);
        entity.setCourses(courses);
        entity = studentService.createOrUpdate(entity);
        return dozerMapper.map(entity, StudentDTO.class, STUDENT_INFO_E_T_D);
    }

    @Override
    public StudentDTO findByPredicate(final Predicate predicate) {
        return dozerMapper.map(studentService.findOne(predicate), StudentDTO.class, STUDENT_INFO_E_T_D);
    }

    @Override
    public Collection<StudentDTO> findAllByPredicate(final Predicate predicate) {
        return studentService.findAll(predicate).stream().map(s ->
                dozerMapper.map(s, StudentDTO.class, STUDENT_INFO_E_T_D)).collect(Collectors.toList());
    }

    @Override
    public Page<StudentDTO> findAllByPredicateAndPage(final Predicate predicate, final Pageable page) {
        return studentService.findAll(predicate, page).map(this::convert);
    }

    private StudentDTO convert(final StudentEntity entity) {
        return dozerMapper.map(entity, StudentDTO.class, STUDENT_INFO_E_T_D);
    }
}

