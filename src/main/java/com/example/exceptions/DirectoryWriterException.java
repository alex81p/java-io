package com.example.exceptions;

public class DirectoryWriterException extends RuntimeException{

    public DirectoryWriterException() {}

    public DirectoryWriterException(String message) { super(message); }

    public DirectoryWriterException(String message, Throwable cause) {
        super(message, cause);
    }
}
