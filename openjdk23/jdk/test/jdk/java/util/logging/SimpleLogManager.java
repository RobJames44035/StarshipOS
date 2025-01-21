/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

import java.util.*;
import java.util.logging.*;
import sun.util.logging.PlatformLogger;

/*
 * @test
 * @bug 8005615
 * @summary A LogManager subclass overrides its own implementation of named
 *          logger (see the subclassing information in the Logger class specification)
 *
 * @modules java.base/sun.util.logging
 *          java.logging
 * @compile -XDignore.symbol.file CustomLogManager.java SimpleLogManager.java
 * @run main/othervm -Djava.util.logging.manager=SimpleLogManager SimpleLogManager
 */
public class SimpleLogManager extends CustomLogManager {
    public static void main(String[] args) {
        String classname = System.getProperty("java.util.logging.manager");
        if (!classname.equals("SimpleLogManager")) {
            throw new RuntimeException("java.util.logging.manager not set");
        }

        Logger logger = Logger.getLogger(SimpleLogManager.class.getName());
        Logger.getLogger("org.foo.bar.Foo");

        // a platform logger used by the system code is just a Logger instance.
        PlatformLogger.getLogger("org.openjdk.core.logger");

        LogManager mgr = LogManager.getLogManager();
        if (mgr != CustomLogManager.INSTANCE || !(mgr instanceof SimpleLogManager)) {
             throw new RuntimeException(LogManager.getLogManager() + " not SimpleLogManager");
        }

        checkCustomLogger(SimpleLogManager.class.getName(), null);
        checkCustomLogger("org.foo.bar.Foo", null);
        checkCustomLogger("org.openjdk.core.logger", "sun.util.logging.resources.logging");

        // ## The LogManager.demandLogger method does not handle custom log manager
        // ## that overrides the getLogger method to return a custom logger
        // ## (see the test case in 8005640).  Logger.getLogger may return
        // ## a Logger instance but LogManager overrides it with a custom Logger
        // ## instance like this case.
        //
        // However, the specification of LogManager and Logger subclassing is
        // not clear whether this is supported or not.  The following check
        // just captures the current behavior.
        if (logger instanceof CustomLogger) {
            throw new RuntimeException(logger + " not CustomLogger");
        }
    }

    private static void checkCustomLogger(String name, String resourceBundleName) {
        CustomLogManager.checkLogger(name, resourceBundleName);
        Logger logger1 = Logger.getLogger(name);
        Logger logger2 = LogManager.getLogManager().getLogger(name);
        if (logger1 != logger2) {
            throw new RuntimeException(logger1 + " != " + logger2);
        }
        if (!(logger1 instanceof CustomLogger)) {
            throw new RuntimeException(logger1 + " not CustomLogger");
        }
    }

    /*
     * This SimpleLogManager overrides the addLogger method to replace
     * the given logger with a custom logger.
     *
     * It's unclear what the recommended way to use custom logger is.
     * A LogManager subclass might override the getLogger method to return
     * a custom Logger and create a new custom logger if not exist so that
     * Logger.getLogger() can return a custom Logger instance but that violates
     * the LogManager.getLogger() spec which should return null if not found.
     */
    public synchronized boolean addLogger(Logger logger) {
        String name = logger.getName();
        if (namedLoggers.containsKey(name)) {
            return false;
        }
        CustomLogger newLogger = new CustomLogger(logger);
        return super.addLogger(newLogger);
    }

    public class CustomLogger extends Logger {
        final Logger keepRef; // keep a strong reference to avoid GC.
        CustomLogger(Logger logger) {
            super(logger.getName(), logger.getResourceBundleName());
            keepRef = logger;
        }
    }
}
