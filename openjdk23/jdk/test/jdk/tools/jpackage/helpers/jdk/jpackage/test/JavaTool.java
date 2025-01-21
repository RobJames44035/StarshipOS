/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */


package jdk.jpackage.test;


import java.nio.file.Path;
import java.util.spi.ToolProvider;

public enum JavaTool {
    JAVA, JAVAC, JPACKAGE, JAR, JLINK, JMOD;

    JavaTool() {
        this.path = Path.of(System.getProperty("java.home")).resolve(
                relativePathInJavaHome()).toAbsolutePath().normalize();
        if (!path.toFile().exists()) {
            throw new RuntimeException(String.format(
                    "Unable to find tool [%s] at path=[%s]", toolName(), path));
        }
    }

    public Path getPath() {
        return path;
    }

    public ToolProvider asToolProvider() {
        return ToolProvider.findFirst(toolName()).orElse(null);
    }

    private Path relativePathInJavaHome() {
        Path path = Path.of("bin", toolName());
        if (TKit.isWindows()) {
            path = path.getParent().resolve(path.getFileName().toString() + ".exe");
        }
        return path;
    }

    private String toolName() {
        return name().toLowerCase();
    }

    private Path path;
}
