package edu.library_management_system.Exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ✅ Handle Resource Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFoundException(
            ResourceNotFoundException ex,
            HttpServletRequest req) {

        ErrorResponseDto dto = ErrorResponseDto.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .error("Not Found")
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .path(req.getRequestURL().toString())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
    }

    // ✅ Handle All Other Exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGeneralException(
            Exception ex,
            HttpServletRequest req) {

        ErrorResponseDto dto = ErrorResponseDto.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Internal Server Error")
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .path(req.getRequestURL().toString())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(dto);
    }
}