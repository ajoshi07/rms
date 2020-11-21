package com.rms.exception.handler;

import com.rms.exception.NoValidRateDataException;
import com.rms.exception.RateException;
import com.rms.exception.NoRecordException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler({NoRecordException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RateException noDataExceptionHandler()
    {
           return new RateException("RateId not found in RMS",404);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({NoValidRateDataException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RateException noValidDataExceptionHandler()
    {
        return new RateException("Internal server error. Please contact admin",500);
    }
}
