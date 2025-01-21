/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.test.lib;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class JDKToolFinder {

    private JDKToolFinder() {
    }

    /**
     * Returns the full path to an executable in jdk/bin based on System
     * property {@code test.jdk} or {@code compile.jdk} (both are set by the jtreg test suite)
     *
     * @return Full path to an executable in jdk/bin
     */
    public static String getJDKTool(String tool) {

        // First try to find the executable in test.jdk
        try {
            return getTool(tool, "test.jdk");
        } catch (FileNotFoundException e) {

        }

        // Now see if it's available in compile.jdk
        try {
            return getTool(tool, "compile.jdk");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Failed to find " + tool +
                    ", looked in test.jdk (" + System.getProperty("test.jdk") +
                    ") and compile.jdk (" + System.getProperty("compile.jdk") + ")");
        }
    }

    /**
     * Returns the full path to an executable in jdk/bin based on System
     * property {@code compile.jdk}
     *
     * @return Full path to an executable in jdk/bin
     */
    public static String getCompileJDKTool(String tool) {
        try {
            return getTool(tool, "compile.jdk");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the full path to an executable in jdk/bin based on System
     * property {@code test.jdk}
     *
     * @return Full path to an executable in jdk/bin
     */
    public static String getTestJDKTool(String tool) {
        try {
            return getTool(tool, "test.jdk");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getTool(String tool, String property) throws FileNotFoundException {
        String jdkPath = System.getProperty(property);

        if (jdkPath == null) {
            throw new RuntimeException(
                    "System property '" + property + "' not set. This property is normally set by jtreg. "
                    + "When running test separately, set this property using '-D" + property + "=/path/to/jdk'.");
        }

        Path toolName = Paths.get("bin", tool + (Platform.isWindows() ? ".exe" : ""));

        Path jdkTool = Paths.get(jdkPath, toolName.toString());
        if (!jdkTool.toFile().exists()) {
            throw new FileNotFoundException("Could not find file " + jdkTool.toAbsolutePath());
        }

        return jdkTool.toAbsolutePath().toString();
    }
}
