/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package jdk.jpackage.test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


/**
 * Tool to compile Java sources and pack in a jar file.
 */
public final class JarBuilder {

    public JarBuilder() {
        sourceFiles = new ArrayList<>();
    }

    public JarBuilder setOutputJar(Path v) {
        outputJar = v;
        return this;
    }

    public JarBuilder setMainClass(String v) {
        mainClass = v;
        return this;
    }

    public JarBuilder addSourceFile(Path v) {
        sourceFiles.add(v);
        return this;
    }

    public JarBuilder setModuleVersion(String v) {
        moduleVersion = v;
        return this;
    }

    public void create() {
        TKit.withTempDirectory("jar-workdir", workDir -> {
            if (!sourceFiles.isEmpty()) {
                new Executor()
                        .setToolProvider(JavaTool.JAVAC)
                        .addArguments("-d", workDir.toString())
                        .addPathArguments(sourceFiles)
                        .execute();
            }

            Files.createDirectories(outputJar.getParent());
            if (Files.exists(outputJar)) {
                TKit.trace(String.format("Delete [%s] existing jar file", outputJar));
                Files.deleteIfExists(outputJar);
            }

            Executor jarExe = new Executor()
                    .setToolProvider(JavaTool.JAR)
                    .addArguments("-c", "-f", outputJar.toString());
            if (moduleVersion != null) {
                jarExe.addArguments(String.format("--module-version=%s",
                        moduleVersion));
            }
            if (mainClass != null) {
                jarExe.addArguments("-e", mainClass);
            }
            jarExe.addArguments("-C", workDir.toString(), ".");
            jarExe.execute();
        });
    }
    private List<Path> sourceFiles;
    private Path outputJar;
    private String mainClass;
    private String moduleVersion;
}
