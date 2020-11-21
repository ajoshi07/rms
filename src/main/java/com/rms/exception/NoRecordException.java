package com.rms.exception;

public class NoRecordException extends RuntimeException{

    public NoRecordException()
    {
    }
   public NoRecordException(String msg)
    {
        super(msg);
    }
}
