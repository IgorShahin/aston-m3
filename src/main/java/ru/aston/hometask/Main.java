package ru.aston.hometask;

import ru.aston.hometask.exception.FileOperationException;
import ru.aston.hometask.service.FileService;

public class Main {
    public static void main(String[] args) {
        FileService fileService = new FileService("data/test.txt");

        try {
            fileService.writeFile("First line\nSecond line");
            fileService.appendLine("Third line");
            fileService.appendLine("Fourth line");

            if (fileService.fileExists()) {
                System.out.println("File: " + fileService.getFilePath());
                System.out.println("Size: " + fileService.getFileSize() + " bytes\n");
            }

            System.out.println("=== readFromFile ===");
            System.out.println(fileService.readFromFile());

            System.out.println("\n=== readLines ===");
            int i = 1;
            for (String line : fileService.readLines()) {
                System.out.println(i++ + ": " + line);
            }

            fileService.deleteFile();
            System.out.println("Deleted: " + !fileService.fileExists());

        } catch (FileOperationException | IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
