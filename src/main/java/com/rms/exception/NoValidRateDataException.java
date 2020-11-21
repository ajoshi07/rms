package com.rms.exception;

public class NoValidRateDataException extends RuntimeException {

    public NoValidRateDataException() {
    }

    public NoValidRateDataException(String msg) {
        super(msg);
    }
}
