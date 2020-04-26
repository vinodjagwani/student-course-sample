package com.school.information.student.service.impl;

import com.querydsl.core.types.Predicate;
import com.school.information.exception.ServiceException;
import com.school.information.exception.constant.ErrorCodeEnum;
import com.school.information.student.repository.CourseRepository;
import com.school.information.student.repository.entity.CourseEntity;
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
public class CourseServiceImpl implements CourseService {


    CourseRepository courseRepository;


    @Override
    public void delete(final Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public CourseEntity findOne(final Predicate predicate) {
        return courseRepository.findOne(predicate).orElseThrow(() -> new ServiceException(ErrorCodeEnum.ENTITY_NOT_FOUND, "Course not found."));
    }

    @Override
    public CourseEntity createOrUpdate(final CourseEntity entity) {
        return courseRepository.save(entity);
    }

    @Override
    public Collection<CourseEntity> findAll(final Predicate predicate) {
        return IterableUtils.toList(courseRepository.findAll(predicate));
    }

    @Override
    public Page<CourseEntity> findAll(final Predicate predicate, final Pageable page) {
        return courseRepository.findAll(predicate, page);
    }
}
