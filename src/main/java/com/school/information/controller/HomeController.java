package com.school.information.controller;


import com.school.information.student.service.StudentService;
import com.school.information.student.web.constant.WebConstant;
import com.school.information.student.web.dto.StudentDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HomeController {


    StudentService studentService;


    @GetMapping("/")
    public ModelAndView renderHomePage() {
        ModelAndView view = new ModelAndView();
        view.addObject("student", new StudentDTO());
        view.setViewName(WebConstant.ADD_STUDENT_INFO_VIEW);
        return view;
    }


}
