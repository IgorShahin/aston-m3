package ru.aston.hometask;

import ru.aston.hometask.exception.FileOperationException;
import ru.aston.hometask.logger.LoggingConfig;
import ru.aston.hometask.service.intf.FileReaderService;
import ru.aston.hometask.service.intf.FileWriterService;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        // Загружаем кастомный logging.properties из resources
        LoggingConfig.init();

        Path path = resolvePath(args).orElseGet(Main::askPathInteractively);
        log.info(() -> "Работаем с файлом: " + path.toAbsolutePath());

        var writerOverwrite = new FileWriterService(path, "Первая строка", false, true);
        var writerAppend = new FileWriterService(path, "Дополнение", true, true);
        var reader = new FileReaderService(path);

        try {
            writerOverwrite.process();
            log.info("Перезапись выполнена (overwrite).");

            writerAppend.process();
            log.info("Добавление выполнено (append).");

            if (reader.exists()) {
                long size = reader.size();
                log.info(() -> "Файл существует. Размер: " + size + " байт.");
            } else {
                log.warning("Файл не найден после записи (что странно).");
            }

            String content = reader.process();
            log.info(() -> "Содержимое файла:\n" + content);

            // пример удаления
            // reader.delete();
            // log.info("Файл удалён.");

        } catch (FileOperationException e) {
            log.log(Level.SEVERE, "Ошибка файловой операции: " + e.getMessage(), e);
        }
    }

    /**
     * 1) --file=/path/to/file или первый позиционный аргумент
     * 2) По умолчанию: data/demo.txt в проекте
     */
    private static Optional<Path> resolvePath(String[] args) {
        if (args != null) {
            for (String arg : args) {
                if (arg != null) {
                    String a = arg.trim();
                    if (a.toLowerCase(Locale.ROOT).startsWith("--file=")) {
                        String value = a.substring("--file=".length());
                        if (isNotBlank(value)) {
                            return Optional.of(Paths.get(value.trim()));
                        }
                    }
                }
            }
            if (args.length > 0 && isNotBlank(args[0])) {
                return Optional.of(Paths.get(args[0].trim()));
            }
        }
        // по умолчанию кладём в папку data
        Path defaultPath = Paths.get("data/demo.txt");
        return Optional.of(defaultPath);
    }

    /** Интерактивный ввод (через логгер, без System.out) */
    private static Path askPathInteractively() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            log.info("Введите путь к файлу (или оставьте пустым для выхода): ");
            String line = sc.nextLine();
            if (isNotBlank(line)) {
                return Paths.get(line.trim());
            }
            log.warning("Путь пустой. Повторите ввод.");
        }
    }

    private static boolean isNotBlank(String s) {
        return s != null && !s.trim().isEmpty();
    }
}