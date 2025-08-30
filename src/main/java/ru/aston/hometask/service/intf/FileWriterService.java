package ru.aston.hometask.service.intf;

import ru.aston.hometask.exception.FileOperationException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public final class FileWriterService implements FileService<Path> {
    private final Path path;
    private final String data;
    private final boolean append;
    private final boolean addNewLine;

    public FileWriterService(Path path, String data, boolean append, boolean addNewLine) {
        if (path == null) throw new IllegalArgumentException("path cannot be null");
        if (data == null) throw new IllegalArgumentException("data cannot be null");
        this.path = path;
        this.data = data;
        this.append = append;
        this.addNewLine = addNewLine;
    }

    public FileWriterService(String fileName, String data, boolean append, boolean addNewLine) {
        this(Path.of(requireNonBlank(fileName)), data, append, addNewLine);
    }

    private static String requireNonBlank(String s) {
        if (s == null || s.trim().isEmpty()) {
            throw new IllegalArgumentException("fileName cannot be null or empty");
        }
        return s;
    }

    @Override
    public Path process() throws FileOperationException {
        try {
            ensureParentExists();
            String payload = addNewLine ? data + System.lineSeparator() : data;
            if (append) {
                Files.writeString(path, payload, StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } else {
                Files.writeString(path, payload, StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            }
            return path;
        } catch (IOException e) {
            throw new FileOperationException("Failed to write file: " + path, e);
        }
    }

    @Override
    public Path getPath() {
        return path;
    }
}
