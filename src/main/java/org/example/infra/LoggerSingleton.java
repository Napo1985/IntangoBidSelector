package org.example.infra;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.*;

public class LoggerSingleton {
    private static final Logger LOGGER = Logger.getLogger("GlobalLogger");

    // ANSI color codes
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String CYAN = "\u001B[36m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String WHITE = "\u001B[97m";

    static {
        configureLogger();
    }

    private static void configureLogger() {
        LOGGER.setUseParentHandlers(false); // Disable default handlers
        ConsoleHandler handler = new ConsoleHandler();

        Level logLevel = loadLogLevelFromConfig();
        LOGGER.setLevel(logLevel);
        handler.setLevel(logLevel);

        handler.setFormatter(new SimpleFormatter() {
            @Override
            public String format(LogRecord record) {
                String color = switch (record.getLevel().getName()) {
                    case "SEVERE" -> RED;
                    case "WARNING" -> YELLOW;
                    case "INFO" -> WHITE;
                    case "FINE" -> CYAN;
                    case "FINER" -> BLUE;
                    case "FINEST" -> PURPLE;
                    default -> RESET;
                };

                return String.format("%s[%s] %s: %s%s%n",
                        color, record.getLevel(),
                        record.getSourceClassName().substring(record.getSourceClassName().lastIndexOf(".") + 1),
                        record.getMessage(), RESET);
            }
        });

        LOGGER.addHandler(handler);
    }

    private static Level loadLogLevelFromConfig() {
        Properties properties = new Properties();
        try (InputStream input = LoggerSingleton.class.getClassLoader().getResourceAsStream("logger.properties")) {
            if (input == null) {
                System.err.println("logger.properties file not found in resources. Using default level: INFO");
                return Level.INFO;
            }
            properties.load(input);
            String level = properties.getProperty("log.level", "INFO").toUpperCase();
            return Level.parse(level);
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error loading logger configuration: " + e.getMessage() + ". Using default level: INFO");
            return Level.INFO; // Default log level
        }
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}
