package com.codoacodo23650.tpgrupo14.exceptions.handler;

import com.codoacodo23650.tpgrupo14.exceptions.dtos.ErrorMessageDto;
import com.codoacodo23650.tpgrupo14.exceptions.exceptionKinds.LoanBadRequestException;
import com.codoacodo23650.tpgrupo14.exceptions.exceptionKinds.UserBadRequestException;
import com.codoacodo23650.tpgrupo14.exceptions.exceptionKinds.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class  GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessageDto> defaultErrorHandler(HttpServletRequest req, Exception e){
        return new ResponseEntity<>(new ErrorMessageDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessageDto> notFoundHandler(HttpServletRequest req, Exception e){
        return new ResponseEntity<>(new ErrorMessageDto(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserBadRequestException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessageDto> badRequestHandler(HttpServletRequest req, Exception e){
        return new ResponseEntity<>(new ErrorMessageDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoanBadRequestException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessageDto> loanBadRequestHandler(HttpServletRequest req, Exception e){
        return new ResponseEntity<>(new ErrorMessageDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
