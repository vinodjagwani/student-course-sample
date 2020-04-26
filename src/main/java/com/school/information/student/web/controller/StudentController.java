package com.school.information.student.web.controller;


import com.querydsl.core.types.Predicate;
import com.school.information.student.repository.entity.QCourseEntity;
import com.school.information.student.repository.entity.QStudentEntity;
import com.school.information.student.repository.entity.StudentEntity;
import com.school.information.student.web.dto.PagerModel;
import com.school.information.student.web.dto.StudentDTO;
import com.school.information.student.web.facade.CourseFacade;
import com.school.information.student.web.facade.StudentFacade;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import static com.school.information.student.web.constant.WebConstant.ADD_STUDENT_INFO_VIEW;
import static com.school.information.student.web.constant.WebConstant.LIST_STUDENT_INFO_VIEW;
import static com.school.information.student.web.constant.WebConstant.LIST_VIEW_COURSE_INFO_VIEW;
import static com.school.information.student.web.constant.WebConstant.RD_LIST_STUDENT_INFO_VIEW;
import static com.school.information.student.web.constant.WebConstant.UPDATE_STUDENT_INFO_VIEW;

@Controller
@RequestMapping("/students")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StudentController {


    StudentFacade studentFacade;

    CourseFacade courseFacade;


    @GetMapping("/{id}/courses")
    public ModelAndView getAllCoursesFromUser(@PathVariable("id") final Long id) {
        ModelAndView view = new ModelAndView();
        view.addObject("courses", studentFacade.findByPredicate(QStudentEntity.studentEntity.id.eq(id)).getCourses());
        view.setViewName(LIST_VIEW_COURSE_INFO_VIEW);
        return view;
    }

    @GetMapping
    public ModelAndView renderStudentPage() {
        ModelAndView view = new ModelAndView();
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setCourses(courseFacade.findAllCoursesByPredicate(QCourseEntity.courseEntity.id.isNotNull()));
        view.addObject("student", studentDTO);
        view.setViewName(ADD_STUDENT_INFO_VIEW);
        return view;
    }

    @GetMapping("/list")
    public ModelAndView getAllStudents(@QuerydslPredicate(root = StudentEntity.class) final Predicate predicate,
                                       @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                       @RequestParam(value = "page", required = false) Integer page) {
        pageSize = ObjectUtils.defaultIfNull(pageSize, INITIAL_PAGE_SIZE);
        page = Objects.nonNull(page) && page > 0 ? page - 1 : INITIAL_PAGE;
        ModelAndView view = new ModelAndView();
        final Page<StudentDTO> dto = studentFacade.findAllByPredicateAndPage(predicate, PageRequest.of(page, pageSize));
        view.addObject("students", dto);
        view.addObject("selectedPageSize", pageSize);
        view.addObject("pageSizes", PAGE_SIZES);
        view.addObject("pager", new PagerModel(dto.getTotalPages(), dto.getNumber(), BUTTONS_TO_SHOW));
        view.setViewName(LIST_STUDENT_INFO_VIEW);
        return view;
    }

    @PostMapping("/add")
    public ModelAndView saveStudent(@Valid final StudentDTO student) {
        studentFacade.createStudent(student);
        ModelAndView view = new ModelAndView();
        view.setViewName(RD_LIST_STUDENT_INFO_VIEW);
        return view;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editStudent(@PathVariable("id") final Long id) {
        ModelAndView view = new ModelAndView();
        final StudentDTO studentDTO = studentFacade.findByPredicate(QStudentEntity.studentEntity.id.eq(id));
        studentDTO.setCourses(courseFacade.findAllCoursesByPredicate(QCourseEntity.courseEntity.id.isNotNull()));
        view.addObject("student", studentDTO);
        view.setViewName(UPDATE_STUDENT_INFO_VIEW);
        return view;
    }

    @PostMapping("/update")
    public ModelAndView updateStudent(@Valid final StudentDTO student) {
        ModelAndView view = new ModelAndView();
        studentFacade.updateStudent(student.getId(), student);
        view.setViewName(RD_LIST_STUDENT_INFO_VIEW);
        return view;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteStudent(@PathVariable("id") long id, final Model model) {
        studentFacade.deleteStudent(id);
        ModelAndView view = new ModelAndView();
        view.setViewName(RD_LIST_STUDENT_INFO_VIEW);
        return view;
    }

}
