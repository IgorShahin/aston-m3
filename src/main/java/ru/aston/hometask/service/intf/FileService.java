package ru.aston.hometask.service.intf;

import ru.aston.hometask.exception.FileOperationException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public interface FileService<R> {
    R process() throws FileOperationException;

    Path getPath();

    default void ensureParentExists() throws FileOperationException {
        try {
            Path parent = getPath().toAbsolutePath().getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
        } catch (IOException e) {
            throw new FileOperationException("Failed to create parent dirs for: " + getPath(), e);
        }
    }

    default boolean exists() {
        return Files.exists(getPath());
    }

    default long size() throws FileOperationException {
        try {
            return Files.size(getPath());
        } catch (IOException e) {
            throw new FileOperationException("Failed to get file size: " + getPath(), e);
        }
    }

    default void delete() throws FileOperationException {
        try {
            Files.deleteIfExists(getPath());
        } catch (IOException e) {
            throw new FileOperationException("Failed to delete file: " + getPath(), e);
        }
    }
}
