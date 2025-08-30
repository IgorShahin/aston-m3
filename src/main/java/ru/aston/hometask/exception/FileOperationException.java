package ru.aston.hometask.exception;

import java.io.IOException;

public class FileOperationException extends IOException {
    public FileOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileOperationException(String message) {
        super(message);
    }
}
