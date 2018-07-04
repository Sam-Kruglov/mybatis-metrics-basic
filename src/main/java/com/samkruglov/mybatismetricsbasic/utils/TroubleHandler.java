package com.samkruglov.mybatismetricsbasic.utils;

import com.samkruglov.mybatismetricsbasic.utils.exceptions.StatusException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@ControllerAdvice
public class TroubleHandler {
    
    private final static Logger log = LoggerFactory.getLogger(TroubleHandler.class);
    
    @ExceptionHandler
    public ResponseEntity<String> handleStatusException(StatusException e) {
    
        log.error(e.getMessage());
        return ResponseEntity.status(e.getStatus()).header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_XML_VALUE)
                             .body(e.getMessage());
    }
    
    @ExceptionHandler
    public ResponseEntity<String> handleException(Exception e) {
        
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_XML_VALUE)
                             .body(e.getMessage());
    }
}
