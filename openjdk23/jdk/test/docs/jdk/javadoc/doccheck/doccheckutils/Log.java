/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
package doccheckutils;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Log {
    private final ArrayList<String> errors;

    private Path baseDir;

    public Log() {
        errors = new ArrayList<>();
    }

    public List<String> getErrors() {
        return errors;
    }

    public void log(Path path, int line, String message, Object... args) {
        errors.add(formatErrorMessage(path, line, message, args));
    }


    public String formatErrorMessage(Path path, int line, String message, Object... args) {
        return path + ":" + line + ": " + formatErrorMessage(message, args);
    }

    public String formatErrorMessage(Path path, int line, Throwable t) {
        return path + ":" + line + ": " + t;
    }

    public String formatErrorMessage(Path path, Throwable t) {
        return path + ": " + t;
    }


    public String formatErrorMessage(String message, Object... args) {
        return String.format(message, args);
    }

    public void log(String message) {
        errors.add(message);
    }

    public void log(Path path, int lineNumber, String s, int errorsOnLine) {
        log(formatErrorMessage(path, lineNumber, s, errorsOnLine));
    }

    public void log(Path path, int line, Throwable t) {
        log(formatErrorMessage(path, line, t));
    }

    public void log(Path path, Throwable t) {
        log(formatErrorMessage(path, t));
    }

    public void log(String message, Object... args) {
        log(formatErrorMessage(message, args));
    }

    public void setBaseDirectory(Path baseDir) {
        this.baseDir = baseDir.toAbsolutePath();
    }

    public Path relativize(Path path) {
        return baseDir != null && path.startsWith(baseDir) ? baseDir.relativize(path) : path;
    }

    public boolean noErrors() {
        return errors.isEmpty();
    }
}
