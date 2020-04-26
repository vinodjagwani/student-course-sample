package com.school.information.student.service.impl;

import com.querydsl.core.types.Predicate;
import com.school.information.exception.ServiceException;
import com.school.information.exception.constant.ErrorCodeEnum;
import com.school.information.student.repository.StudentRepository;
import com.school.information.student.repository.entity.StudentEntity;
import com.school.information.student.service.StudentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;


@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class StudentServiceImpl implements StudentService {


    StudentRepository studentRepository;

    @Override
    public void delete(final Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public StudentEntity createOrUpdate(final StudentEntity entity) {
        return studentRepository.save(entity);
    }

    @Override
    public StudentEntity findOne(final Predicate predicate) {
        return studentRepository.findOne(predicate).orElseThrow(() -> new ServiceException(ErrorCodeEnum.ENTITY_NOT_FOUND, "Student not found."));
    }

    @Override
    public Collection<StudentEntity> findAll(final Predicate predicate) {
        return IterableUtils.toList(studentRepository.findAll(predicate));
    }

    @Override
    public Page<StudentEntity> findAll(Predicate predicate, Pageable page) {
        return studentRepository.findAll(predicate, page);
    }
}
