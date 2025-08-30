package ru.aston.hometask.logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LoggingConfig {
    private static final Logger log = Logger.getLogger(LoggingConfig.class.getName());

    public static void init() {
        try (InputStream in = LoggingConfig.class.getClassLoader()
                .getResourceAsStream("logging.properties")) {
            if (in != null) {
                LogManager.getLogManager().readConfiguration(in);
                log.info("Custom logging.properties loaded from resources");
            } else {
                log.warning("logging.properties not found in resources, using default JUL config");
            }
        } catch (IOException e) {
            log.severe("Failed to load logging.properties: " + e.getMessage());
        }
    }
}
