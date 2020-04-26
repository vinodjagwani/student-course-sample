package com.school.information.student.web.controller;


import com.querydsl.core.types.Predicate;
import com.school.information.student.repository.entity.CourseEntity;
import com.school.information.student.repository.entity.QCourseEntity;
import com.school.information.student.web.constant.WebConstant;
import com.school.information.student.web.dto.CourseDTO;
import com.school.information.student.web.dto.PagerModel;
import com.school.information.student.web.facade.CourseFacade;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Objects;

import static com.school.information.student.web.constant.Constant.BUTTONS_TO_SHOW;
import static com.school.information.student.web.constant.Constant.INITIAL_PAGE;
import static com.school.information.student.web.constant.Constant.INITIAL_PAGE_SIZE;
import static com.school.information.student.web.constant.Constant.PAGE_SIZES;
import static com.school.information.student.web.constant.WebConstant.LIST_COURSE_INFO_VIEW;
import static com.school.information.student.web.constant.WebConstant.RD_LIST_COURSE_INFO_VIEW;
import static com.school.information.student.web.constant.WebConstant.UPDATE_COURSE_INFO_VIEW;

@Controller
@RequestMapping("/courses")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CourseController {


    CourseFacade courseFacade;


    @GetMapping
    public ModelAndView renderCoursePage() {
        ModelAndView view = new ModelAndView();
        view.addObject("course", new CourseDTO());
        view.setViewName(WebConstant.ADD_COURSE_INFO_VIEW);
        return view;
    }

    @GetMapping("/list")
    public ModelAndView getAllCourses(@QuerydslPredicate(root = CourseEntity.class) final Predicate predicate,
                                      @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                      @RequestParam(value = "page", required = false) Integer page) {
        pageSize = ObjectUtils.defaultIfNull(pageSize, INITIAL_PAGE_SIZE);
        page = Objects.nonNull(page) && page > 0 ? page - 1 : INITIAL_PAGE;
        ModelAndView view = new ModelAndView();
        final Page<CourseDTO> dto = courseFacade.findAllCoursesByPredicateAndPage(predicate, PageRequest.of(page, pageSize));
        view.addObject("courses", dto);
        view.addObject("selectedPageSize", pageSize);
        view.addObject("pageSizes", PAGE_SIZES);
        view.addObject("pager", new PagerModel(dto.getTotalPages(), dto.getNumber(), BUTTONS_TO_SHOW));
        view.setViewName(LIST_COURSE_INFO_VIEW);
        return view;
    }

    @PostMapping("/add")
    public ModelAndView saveCourse(@Valid final CourseDTO courseDTO) {
        courseFacade.createCourse(courseDTO);
        ModelAndView view = new ModelAndView();
        view.setViewName(RD_LIST_COURSE_INFO_VIEW);
        return view;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editCourse(@PathVariable("id") final Long id) {
        ModelAndView view = new ModelAndView();
        view.addObject("course", courseFacade.findCourseByPredicate(QCourseEntity.courseEntity.id.eq(id)));
        view.setViewName(UPDATE_COURSE_INFO_VIEW);
        return view;
    }

    @PostMapping("/update")
    public ModelAndView updateCourse(@Valid final CourseDTO courseDTO) {
        ModelAndView view = new ModelAndView();
        courseFacade.updateCourse(courseDTO.getId(), courseDTO);
        view.setViewName(RD_LIST_COURSE_INFO_VIEW);
        return view;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteCourse(@PathVariable("id") long id) {
        courseFacade.deleteCourse(id);
        ModelAndView view = new ModelAndView();
        view.setViewName(RD_LIST_COURSE_INFO_VIEW);
        return view;
    }
}
