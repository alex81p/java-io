package com.example.exceptions;

public class DirectoryAnalyzerException extends RuntimeException{

    public DirectoryAnalyzerException() {
    }

    public DirectoryAnalyzerException(String message) {
        super(message);
    }

    public DirectoryAnalyzerException(String message, Throwable cause) {
        super(message, cause);
    }
}
