/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

//file:noinspection GroovyAssignabilityCheck
//file:noinspection unused
package org.starship.config

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.FileAppender
import ch.qos.logback.core.util.StatusPrinter
import groovy.util.logging.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Slf4j

/**
 * This class provides default and dynamic logging configuration
 * for the StarshipOS application using Logback. It includes methods
 * to initialize a default logger configuration and reconfigure 
 * logging settings dynamically at runtime.
 *
 * Key Features:
 * - Initializes default logging to console and file.
 * - Supports dynamic reconfiguration of log levels and output file locations.
 *
 * Usage:
 * - To set default logging, call `LoggingConfig.initializeDefaultLogger()`.
 * - To dynamically configure logging, call `LoggingConfig.configure(Map args)`.
 *
 * Example for dynamic configuration:
 *
 * ```groovy
 * LoggingConfig.configure(level: "DEBUG", location: "/path/to/logfile.log")
 * ```
 *
 * Dependencies:
 * - Logback classic (ch.qos.logback.classic)
 * - Groovy @Slf4j annotation
 * - SLF4J for logging interfaces
 *
 * @since 1.0
 * @author R.A. James
 */
class LoggingConfig {
    private static final Logger logger = LoggerFactory.getLogger(LoggingConfig)


    /**
    * Initializes the default logger configuration for the application.
    *
    * This method sets up the logging framework (Logback) with default settings,
    * including both console and file appenders. It formats logs using a
    * default pattern and sets the log level to INFO.
    *
    * Features:
    * - Console logging for immediate feedback in the terminal.
    * - File logging for persistent storage in a default location.
    * - Logs are formatted in a consistent and readable pattern.
    *
    * Key Steps:
    * - Resets any existing logger configuration.
    * - Creates and configures appenders for console and file logging.
    * - Associates the appenders with the root logger.
    *
    * Logging format:
    * - Logs are timestamped, include the log level, thread name, logger name,
    *   and the message.
    *
    * Exception Handling:
    * - Prints an error to standard error if initialization fails, ensuring no crash.
    *
    * Example Output:
    * ```
    * 2025-01-01 12:34:56 INFO  [main] org.starship.config.LoggingConfig - Sample log message
    * ```
    *
    * Internal Usage:
    * - Automatically invoked when the application starts to ensure
    *   default behavior unless dynamically reconfigured at runtime.
    *
    * @throws Exception If there is an error during the logger initialization,
    *                   it catches and logs the error message.
    *
    * @since 1.0
    */
    static void initializeDefaultLogger() {
        try {
            LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory()
            context.reset() // Reset existing configuration to start fresh

            // Console appender: Prints logs to stdout by default
            ConsoleAppender consoleAppender = new ConsoleAppender()
            consoleAppender.setContext(context)
            consoleAppender.setName("console")
            
            // File appender: Saves logs to a file
            FileAppender fileAppender = new FileAppender()
            fileAppender.setName("file")
            fileAppender.setContext(context)
            fileAppender.setFile("/home/rajames/IdeaProjects/StarshipOS/ships.log")

            // Set log pattern (default format for logs)
            PatternLayoutEncoder encoder = new PatternLayoutEncoder()
            encoder.setContext(context)
            encoder.setPattern("%d{yyyy-MM-dd HH:mm:ss} %-5level [%thread] %logger{36} - %msg%n")
            encoder.start()
            consoleAppender.setEncoder(encoder)
            consoleAppender.start()
            
            encoder = new PatternLayoutEncoder()
            encoder.setContext(context)
            encoder.setPattern("%d{yyyy-MM-dd HH:mm:ss} %-5level [%thread] %logger{36} - %msg%n")
            encoder.start()
            fileAppender.setEncoder(encoder)
            fileAppender.start()

            // Attach the appender to the root logger
            ch.qos.logback.classic.Logger rootLogger = context.getLogger("ROOT")
            rootLogger.addAppender(consoleAppender)
            rootLogger.setLevel(Level.INFO) // Default logging level: INFO

            StatusPrinter.print(context) // Print logback's internal status for debugging
        } catch (Exception e) {
            System.err.println("Failed to initialize default logger: " + e.message)
        }
    }
    
    /**
    * Dynamically configures logging settings at runtime.
    *
    * This method allows updating the logging level and/or log file location
    * during the application's execution. Depending on the arguments provided,
    * it will update the root logger configuration.
    *
    * @param args A map containing the configuration options. Supported keys:
    *             - `level` (String): The logging level to set, e.g., "DEBUG", "INFO", "ERROR".
    *             - `location` (String): File path to the log file where logs should be written.
    *
    * Example usage:
    * ```groovy
    * LoggingConfig.configure(level: "DEBUG", location: "/path/to/logfile.log")
    * ```
    *
    * Key steps:
    * 1. If `level` is provided, the root logger's level is updated.
    * 2. If `location` is provided, the logger writes logs to the specified file.
    *
    * Exception Handling:
    * - Logs errors if configuration fails, allowing graceful recovery on issues.
    *
    * @since 1.0
    */
    static void configure(Map args) {
        try {
            LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory()
            ch.qos.logback.classic.Logger rootLogger = context.getLogger("ROOT")

            if (args.level) {
                rootLogger.setLevel(Level.toLevel(args.level.toUpperCase(), Level.INFO))
                logger.info("Log level set to: ${args.level}")
            }

            if (args.location) {
                // File appender: Saves logs to a file
                FileAppender fileAppender = new FileAppender()
                fileAppender.setName("file")
                fileAppender.setContext(context)
                fileAppender.setFile(args.location)

                // Reuse the pattern layout encoder
                PatternLayoutEncoder encoder = new PatternLayoutEncoder()
                encoder.setContext(context)
                encoder.setPattern("%d{yyyy-MM-dd HH:mm:ss} %-5level [%thread] %logger{36} - %msg%n")
                encoder.start()
                fileAppender.setEncoder(encoder)
                fileAppender.start()

                // Remove all existing appenders and attach only the file appender
                rootLogger.detachAndStopAllAppenders()
                rootLogger.addAppender(fileAppender)
                logger.info("Logging to file: ${args.location}")
            }
        } catch (Exception e) {
            logger.error("Failed to configure logging: ${e.message}", e)
        }
    }
}
