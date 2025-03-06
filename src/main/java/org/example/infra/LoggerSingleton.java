package org.example.infra;

import java.util.logging.*;

public class LoggerSingleton {
    private static final Logger LOGGER = Logger.getLogger("GlobalLogger");

    // ANSI color codes
    private static final String RESET = "\u001B[0m";      // Reset color
    private static final String RED = "\u001B[31m";       // SEVERE - Red
    private static final String YELLOW = "\u001B[33m";    // WARNING - Yellow
    private static final String CYAN = "\u001B[36m";      // FINE - Cyan
    private static final String BLUE = "\u001B[34m";      // FINER - Blue
    private static final String PURPLE = "\u001B[35m";    // FINEST - Purple
    private static final String WHITE = "\u001B[0m";      // INFO - White

    static {
        configureLogger();
    }

    private static void configureLogger() {
        LOGGER.setUseParentHandlers(false); // Disable default handlers

        ConsoleHandler handler = new ConsoleHandler() {
            @Override
            protected void setOutputStream(java.io.OutputStream out) throws SecurityException {
                super.setOutputStream(System.out); // Redirect logs to stdout
            }
        };

        handler.setLevel(Level.FINEST);  // Allow all levels
        LOGGER.setLevel(Level.FINEST);   // Capture all log levels

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

    public static Logger getLogger() {
        return LOGGER;
    }
}
