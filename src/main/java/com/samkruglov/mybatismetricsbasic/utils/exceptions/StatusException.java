package com.samkruglov.mybatismetricsbasic.utils.exceptions;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class StatusException extends RuntimeException {
    
    HttpStatus status;
    
    public StatusException(final String message, final HttpStatus status) {
        
        super(message);
        this.status = status;
    }
    
    public StatusException(final String message, final Throwable cause, final HttpStatus status) {
        
        super(message, cause);
        this.status = status;
    }
}
