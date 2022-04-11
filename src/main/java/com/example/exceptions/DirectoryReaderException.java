package com.example.exceptions;

public class DirectoryReaderException extends RuntimeException{

    public DirectoryReaderException() {
    }

    public DirectoryReaderException(String message) {
        super(message);
    }

    public DirectoryReaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
