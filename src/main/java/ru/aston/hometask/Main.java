package ru.aston.hometask;

import ru.aston.hometask.exception.FileOperationException;
import ru.aston.hometask.service.FileService;

public class Main {
    public static void main(String[] args) {
        FileService fileService = new FileService("/root/protected.txt");

        try {
            fileService.writeFile("Trying to write into a protected directory...");
        } catch (FileOperationException e) {
            System.err.println("Caught FileOperationException:");
            System.err.println("Message: " + e.getMessage());

            if (e.getCause() != null) {
                System.err.println("Original cause: " + e.getCause().getClass().getSimpleName());
                System.err.println("Cause message: " + e.getCause().getMessage());
            }
        }
    }
}