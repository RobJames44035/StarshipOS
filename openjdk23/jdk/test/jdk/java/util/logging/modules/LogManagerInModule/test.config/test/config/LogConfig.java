/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package test.config;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import test.handlers.TestHandler;

/**
 * A dummy class that configures the logging system.
 * @author danielfuchs
 */
public class LogConfig {
    private static final List<Logger> LOGGERS = new ArrayList<>();
    public LogConfig() {
        LogManager manager = LogManager.getLogManager();
        Logger logger = Logger.getLogger("com.xyz.foo");
        if (logger.getHandlers().length > 0) {
            System.err.println(this.getClass().getName() + ": "
                    + "Unexpected handlers: "
                    + List.of(logger.getHandlers()));
            throw new RuntimeException("Unexpected handlers: "
                    + List.of(logger.getHandlers()));
        }
        logger.addHandler(new TestHandler());
        LOGGERS.add(logger);
    }
}
