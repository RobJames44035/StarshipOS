/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package sun.tools.common;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * A helper class that retrieves the main class name for
 * a running Java process using the proc filesystem (procfs)
 */
final class ProcessHelper {

    private static final String CMD_PREFIX = "cmd:";

    /**
     * Gets the main class name for the given Java process by parsing the
     * process command line. If the application was started with the <em>-jar</em>
     * option this method returns the name of the jar file. If the application
     * was started with <em>-m</em> or <em>--module</em> option, the method returns
     * the module name and the main class name.
     * @param pid - process ID (pid)
     * @return the main class name or null if the process no longer exists or
     * was started with a native launcher (e.g. jcmd etc)
     */
    static String getMainClass(String pid) {
        String cmdLine = getCommandLine(pid);
        if (cmdLine == null) {
            return null;
        }
        if (cmdLine.startsWith(CMD_PREFIX)) {
            cmdLine = cmdLine.substring(CMD_PREFIX.length());
        }
        String[] parts = cmdLine.split("\0");
        String mainClass = null;

        if (parts.length == 0) {
            return null;
        }

        // Check the executable
        String[] executablePath = parts[0].split("/");
        if (executablePath.length > 0) {
            String binaryName = executablePath[executablePath.length - 1];
            if (!"java".equals(binaryName)) {
                // Skip the process if it is not started with java launcher
                return null;
            }
        }

        // To be consistent with the behavior on other platforms, if -jar, -m, or --module
        // options are used then just return the value (the path to the jar file or module
        // name with a main class). Otherwise, the main class name is the first part that
        // is not a Java option (doesn't start with '-' and is not a classpath or a module
        // whitespace option).

        for (int i = 1; i < parts.length && mainClass == null; i++) {
            if (i < parts.length - 1) {
                if (parts[i].equals("-m") || parts[i].equals("--module") || parts[i].equals("-jar")) {
                    return parts[i + 1];
                }
            }

            if (parts[i].startsWith("--module=")) {
                return parts[i].substring("--module=".length());
            }

            // If this is a classpath or a module whitespace option then skip the next part
            // (the classpath or the option value itself)
            if (parts[i].equals("-cp") || parts[i].equals("-classpath") ||  parts[i].equals("--class-path") ||
                    isModuleWhiteSpaceOption(parts[i])) {
                i++;
                continue;
            }
            // Skip all other Java options
            if (parts[i].startsWith("-")) {
                continue;
            }

            // If it is a source-file mode then return null
            if (parts[i].endsWith(".java")) {
                return null;
            }

            mainClass = parts[i];
        }
        return mainClass;
    }

    private static String getCommandLine(String pid) {
        try (Stream<String> lines =
                     Files.lines(Paths.get("/proc/" + pid + "/cmdline"))) {
            return lines.findFirst().orElse(null);
        } catch (IOException | UncheckedIOException e) {
            return null;
        }
    }

    private static boolean isModuleWhiteSpaceOption(String option) {
        return option.equals("-p") ||
               option.equals("--module-path") ||
               option.equals("--upgrade-module-path") ||
               option.equals("--add-modules") ||
               option.equals("--limit-modules") ||
               option.equals("--add-exports") ||
               option.equals("--add-opens") ||
               option.equals("--add-reads") ||
               option.equals("--patch-module");
    }
}
