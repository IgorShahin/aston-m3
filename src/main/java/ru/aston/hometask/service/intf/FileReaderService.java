package ru.aston.hometask.service.intf;

import ru.aston.hometask.exception.FileOperationException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class FileReaderService implements FileService<String> {
    private final Path path;

    public FileReaderService(Path path) {
        if (path == null) throw new IllegalArgumentException("path cannot be null");
        this.path = path;
    }

    public FileReaderService(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("fileName cannot be null or empty");
        }
        this.path = Path.of(fileName);
    }

    @Override
    public String process() throws FileOperationException {
        try {
            return Files.readString(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new FileOperationException("Failed to read file: " + path, e);
        }
    }

    @Override
    public Path getPath() {
        return path;
    }
}
