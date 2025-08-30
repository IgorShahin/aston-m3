package ru.aston.hometask.service.intf;

import ru.aston.hometask.exception.FileOperationException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class FileService2 {
    private final Path filePath;

    public FileService2(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty");
        }
        this.filePath = Path.of(fileName);
    }

    public FileService2(Path filePath) {
        this.filePath = Objects.requireNonNull(filePath, "filePath cannot be null");
    }

    private void ensureParentExists() throws IOException {
        Path parent = filePath.toAbsolutePath().getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
    }

    public void writeFile(String data) throws FileOperationException {
        if (data == null) {
            throw new IllegalArgumentException("Data to write cannot be null");
        }
        try {
            ensureParentExists();
            Files.writeString(filePath, data, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new FileOperationException("Failed to write to file: " + filePath, e);
        }
    }

    public void appendLine(String line) throws FileOperationException {
        if (line == null) {
            throw new IllegalArgumentException("Line to append cannot be null");
        }
        try {
            ensureParentExists();
            Files.writeString(filePath, line + System.lineSeparator(), StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new FileOperationException("Failed to append to file: " + filePath, e);
        }
    }

    public String readFromFile() throws FileOperationException {
        try {
            return Files.readString(filePath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new FileOperationException("Failed to read file: " + filePath, e);
        }
    }


    public List<String> readLines() throws FileOperationException {
        try {
            return Files.readAllLines(filePath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new FileOperationException("Failed to read lines from file: " + filePath, e);
        }
    }

    public Stream<String> linesStream() throws FileOperationException {
        try {
            return Files.lines(filePath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new FileOperationException("Failed to open lines stream: " + filePath, e);
        }
    }

    public void deleteFile() throws FileOperationException {
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new FileOperationException("Failed to delete file: " + filePath, e);
        }
    }

    public boolean fileExists() {
        return Files.exists(filePath);
    }

    public long getFileSize() throws FileOperationException {
        try {
            return Files.size(filePath);
        } catch (IOException e) {
            throw new FileOperationException("Failed to get file size: " + filePath, e);
        }
    }

    public Path getFilePath() {
        return filePath;
    }
}