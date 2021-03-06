package com.bwagih.automaaticirrigateplot.exception;

import com.bwagih.automaaticirrigateplot.model.ErrorDetails;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author bwagih
 */

@ControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneralExceptionHandler.class);


    @ExceptionHandler(NotfoundException.class)
    public final ResponseEntity<ErrorDetails> handleUserNoDataFoundException(NotfoundException ex, WebRequest request) {
        LOGGER.warn(ex.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BusinessException.class)
    public final ResponseEntity<ErrorDetails> handleBusinessException(BusinessException ex, WebRequest request) {
        LOGGER.warn(ex.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                request.getDescription(false));
        if (ex.getParams() != null) {
            errorDetails.setParamObjects(ex.getParams());
        }
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_ACCEPTABLE);
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request)
    {
        ErrorDetails errorDetails =
                new ErrorDetails(new Date(),  "Validation Failed", ex.getBindingResult().toString());
        //returning exception structure and specific status
        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }
//   @ExceptionHandler(Exception.class)
//    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
//        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
//                request.getDescription(false));
//        return new ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
}
