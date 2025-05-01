package com.spring.multitenant.controller;


import static com.spring.multitenant.common.CommonConstants.DEFAULT_ERROR_CODE;
import static com.spring.multitenant.common.CommonConstants.UNEXPECTED_ERROR;

import com.spring.multitenant.dto.PractiseError;
import com.spring.multitenant.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * This Base Controller should be extented in all the subsequent REST controllers.
 *
 * All the common logics for REST controllers should be included here.
 *
 */
@Slf4j
public class BaseController {

    /**
     * Translate Service Exceptions in to a custom error
     *
     * @param e Service Exception
     * @param request HTTP Request
     * @param response HTTP Response
     * @return Custom Error object to be returned from the rest api
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public PractiseError handleServiceException(ServiceException e, HttpServletRequest request, HttpServletResponse response) {
        log.error("Service Exception occurred: ", e);
        PractiseError PractiseError = new PractiseError();
        try {
            PractiseError.setStatus(Integer.parseInt(e.getErrorCode()));
        } catch (NumberFormatException numberFormatException) {
            PractiseError.setStatus(DEFAULT_ERROR_CODE);
        }
        PractiseError.setDescription("Service Exception occurred");
        PractiseError.setAdditionalInfo(e.getMessage());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return PractiseError;
    }

    /**
     * Translate Global Exceptions in to a custom error Object
     *
     * @param e Exception
     * @param request HTTP Request
     * @param response HTTP Response
     * @return Custom Error object to be returned from the rest api
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public PractiseError handleException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        log.error("Unexpected Exception occurred: ", e);
        PractiseError PractiseError = new PractiseError();
        PractiseError.setStatus(DEFAULT_ERROR_CODE);
        PractiseError.setDescription(UNEXPECTED_ERROR);
        PractiseError.setAdditionalInfo(UNEXPECTED_ERROR + ": " + e.getMessage());
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return PractiseError;
    }
}
