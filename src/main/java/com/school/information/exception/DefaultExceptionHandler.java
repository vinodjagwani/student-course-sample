package com.school.information.exception;


import com.school.information.exception.constant.DbConstraintMapping;
import com.school.information.exception.constant.ErrorCodeEnum;
import com.school.information.exception.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.school.information.student.web.constant.WebConstant.ERROR_VIEW;

@Slf4j
@ControllerAdvice(annotations = Controller.class)
@RequestMapping(produces = MediaType.APPLICATION_PROBLEM_JSON_VALUE)
public class DefaultExceptionHandler {

    private static final String EXCEPTION_OCCURRED_MSG = "An exception occurred: ";

    @ExceptionHandler(Throwable.class)
    public ModelAndView handleException(final Throwable ex) {
        log.error(EXCEPTION_OCCURRED_MSG, ex);
        return buildModelAndView(ERROR_VIEW, buildErrorResponse(ExceptionUtils.getRootCauseMessage(ex), Collections.emptyList()));
    }

    @ExceptionHandler(BindException.class)
    public String handleException(final BindException ex, final Model model, final BindingResult result) {
        log.error(EXCEPTION_OCCURRED_MSG, ex);
        List<ErrorResponse.ErrorInfo> errorsInfo = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError ->
                errorsInfo.add(ErrorResponse.ErrorInfo.builder().domain(fieldError.getField())
                        .message(fieldError.getDefaultMessage()).reason(ErrorCodeEnum.INVALID_PARAM.name()).build()));
        model.addAttribute("errorResponse", buildErrorResponse(ex.getMessage(), errorsInfo));
        return ERROR_VIEW;
    }


    @ExceptionHandler(ServiceException.class)
    public ModelAndView handleException(final ServiceException ex) {
        log.error(EXCEPTION_OCCURRED_MSG, ex);
        return buildModelAndView(ERROR_VIEW, buildErrorResponse(ExceptionUtils.getRootCauseMessage(ex), Collections.emptyList()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ModelAndView handleException(final HttpMessageNotReadableException ex) {
        log.error(EXCEPTION_OCCURRED_MSG, ex);
        return buildModelAndView(ERROR_VIEW, buildErrorResponse(ExceptionUtils.getRootCauseMessage(ex), Collections.emptyList()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ModelAndView handleException(final MethodArgumentNotValidException ex) {
        log.error(EXCEPTION_OCCURRED_MSG, ex);
        List<ErrorResponse.ErrorInfo> errorsInfo = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError ->
                errorsInfo.add(ErrorResponse.ErrorInfo.builder().domain(fieldError.getField())
                        .message(fieldError.getDefaultMessage()).reason(ErrorCodeEnum.INVALID_PARAM.name()).build()));
        return buildModelAndView(ERROR_VIEW, buildErrorResponse(ex.getMessage(), errorsInfo));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ModelAndView handleException(final HttpRequestMethodNotSupportedException ex) {
        log.error(EXCEPTION_OCCURRED_MSG, ex);
        return buildModelAndView(ERROR_VIEW, buildErrorResponse(ex.getMessage(), Collections.emptyList()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView handleException(final IllegalArgumentException ex) {
        log.error(EXCEPTION_OCCURRED_MSG, ex);
        return buildModelAndView(ERROR_VIEW, buildErrorResponse(ex.getMessage(), Collections.emptyList()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ModelAndView handleException(final MissingServletRequestParameterException ex) {
        log.error(EXCEPTION_OCCURRED_MSG, ex);
        List<ErrorResponse.ErrorInfo> errorsInfo = new ArrayList<>();
        errorsInfo.add(ErrorResponse.ErrorInfo.builder().domain(ex.getParameterName())
                .message(ex.getMessage()).reason(ErrorCodeEnum.INVALID_PARAM.name()).build());
        return buildModelAndView(ERROR_VIEW, buildErrorResponse(ex.getMessage(), errorsInfo));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ModelAndView handleException(final ConstraintViolationException ex) {
        log.error(EXCEPTION_OCCURRED_MSG, ex);
        return buildModelAndView(ERROR_VIEW, buildErrorResponse(ex.getMessage(), Collections.emptyList()));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ModelAndView handleException(final HttpMediaTypeNotSupportedException ex) {
        log.error(EXCEPTION_OCCURRED_MSG, ex);
        return buildModelAndView(ERROR_VIEW, buildErrorResponse(ex.getMessage(), Collections.emptyList()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView handleException(final DataIntegrityViolationException ex) {
        log.error(EXCEPTION_OCCURRED_MSG, ex);
        final Throwable th = ex.getCause();
        String message = NestedExceptionUtils.getMostSpecificCause(ex).getMessage();
        if (th instanceof org.hibernate.exception.ConstraintViolationException) {
            final String constraintName = ((org.hibernate.exception.ConstraintViolationException) th).getConstraintName();
            final Map<String, String> map = DbConstraintMapping.getConstraintByName();
            message = map.keySet().stream().filter(k -> StringUtils.isNotEmpty(constraintName) && constraintName.contains(k)).findFirst()
                    .map(map::get).orElse(ObjectUtils.defaultIfNull(constraintName, message));
        }
        return buildModelAndView(ERROR_VIEW, buildErrorResponse(message, Collections.emptyList()));
    }

    @ExceptionHandler(JpaSystemException.class)
    public ModelAndView handleException(final JpaSystemException ex) {
        log.error(EXCEPTION_OCCURRED_MSG, ex);
        final String message = NestedExceptionUtils.getMostSpecificCause(ex).getMessage();
        return buildModelAndView(ERROR_VIEW, buildErrorResponse(StringUtils.remove(message, "\""), Collections.emptyList()));
    }

    private ErrorResponse buildErrorResponse(final String message, final List<ErrorResponse.ErrorInfo> errorsInfo) {
        return ErrorResponse.builder().code(1).message(message).errors(errorsInfo).build();
    }

    private ModelAndView buildModelAndView(final String view, final ErrorResponse errorResponse) {
        ModelAndView modelAndView = new ModelAndView(view);
        modelAndView.addObject("errorResponse", errorResponse);
        return modelAndView;
    }
}
