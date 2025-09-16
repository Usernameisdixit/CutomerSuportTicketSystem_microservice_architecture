package com.hex.ticket_service.exception;

import com.hex.ticket_service.dto.ApiError;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

  private ApiError buildError(Exception ex, WebRequest request, HttpStatus status)
  {
 return  new ApiError(ex.getMessage(),request.getDescription(false),status.value(), LocalDateTime.now());
  }


  @ExceptionHandler(UnauthorizedActionException.class)
  public ResponseEntity<ApiError> handleUnauthorizedActionException(UnauthorizedActionException ex, WebRequest request)
  {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(buildError(ex,request,HttpStatus.FORBIDDEN));
  }
//400
    @ExceptionHandler(InvalidAssignmentException.class)
    public ResponseEntity<ApiError> handleInvalidAssignmentException(InvalidAssignmentException ex, WebRequest request)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildError(ex,request,HttpStatus.BAD_REQUEST));
    }


    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<ApiError> handleTicketNotFoundException(TicketNotFoundException ex, WebRequest request)
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildError(ex,request,HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(InvalidTicketDataException.class)
    public ResponseEntity<ApiError> handleInvalidTicketDataException(InvalidTicketDataException ex, WebRequest request)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildError(ex,request,HttpStatus.BAD_REQUEST));
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFoundException(UserNotFoundException ex, WebRequest request)
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildError(ex,request,HttpStatus.NOT_FOUND));
    }



//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiError> handleGeneric(Exception ex, WebRequest request)
//    {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(buildError(ex,request,HttpStatus.INTERNAL_SERVER_ERROR));
//    }


}
