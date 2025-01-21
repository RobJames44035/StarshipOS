/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package metaspace.stressHierarchy.common;

import jdk.test.lib.JDKToolLauncher;
import jdk.test.lib.Utils;
import jdk.test.lib.process.ProcessTools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class GenClassesBuilder {
    public static void main(String[] args) {
        Path srcDst = Paths.get("genSrc").toAbsolutePath();
        Path classesDir = Paths.get(Utils.TEST_CLASSES).toAbsolutePath();
        generateSource(srcDst);
        compileSource(srcDst, classesDir);
    }

    private static void compileSource(Path srcDst, Path classesDir) {
        JDKToolLauncher javac = JDKToolLauncher.create("javac")
                                               .addToolArg("-d")
                                               .addToolArg(classesDir.toString())
                                               .addToolArg("-cp")
                                               .addToolArg(Utils.TEST_CLASS_PATH);
        try (Stream<Path> stream = Files.walk(srcDst)) {
            stream.map(Path::toAbsolutePath)
                  .map(Path::toString)
                  .filter(s -> s.endsWith(".java"))
                  .forEach(javac::addToolArg);
        } catch (IOException e) {
            throw new Error("traverse source dir " + srcDst, e);
        }
        String[] command = javac.getCommand();
        try {
            ProcessTools.executeCommand(command)
                        .shouldHaveExitValue(0);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            throw new Error("execution of javac(" + Arrays.toString(command) + ") failed", e);
        }
    }

    private static void generateSource(Path dir) {
        try {
            Files.createDirectories(dir);
        } catch (IOException e) {
            throw new Error("can't create dirs for" + dir, e);
        }

        try {
            HumongousClassGen.main(new String[]{dir.toString()});
        } catch (Exception e) {
            throw new Error("can't generate classes", e);
        }
    }
}

