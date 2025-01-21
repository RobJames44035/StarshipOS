/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package nsk.share;

import jdk.test.lib.JDKToolLauncher;
import jdk.test.lib.Utils;
import jdk.test.lib.process.ProcessTools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class ExtraClassesBuilder {
    public static void main(String[] args) {
        String[] javacOpts = Arrays.stream(args)
                                   .takeWhile(s -> s.startsWith("-"))
                                   .toArray(String[]::new);

        Arrays.stream(args)
              .dropWhile(s -> s.startsWith("-"))
              .forEach(s -> ExtraClassesBuilder.compile(s, javacOpts));
    }

    private static void compile(String name, String[] args) {
        Path src = Paths.get(Utils.TEST_SRC)
                        .resolve(name)
                        .toAbsolutePath();
        if (Files.notExists(src)) {
            throw new Error(src + " doesn't exist");
        }
        Path dst = Paths.get("bin")
                        .resolve(Paths.get(name).getFileName())
                        .toAbsolutePath();
        try {
            Files.createDirectories(dst);
        } catch (IOException e) {
            throw new Error("can't create dir " + dst, e);
        }
        JDKToolLauncher javac = JDKToolLauncher.create("javac")
                                               .addToolArg("-d")
                                               .addToolArg(dst.toString())
                                               .addToolArg("-cp")
                                               .addToolArg(Utils.TEST_CLASS_PATH);

        for (String arg : args) {
            javac.addToolArg(arg);
        }

        try (Stream<Path> stream = Files.walk(src)) {
            stream.map(Path::toAbsolutePath)
                  .map(Path::toString)
                  .filter(s -> s.endsWith(".java"))
                  .forEach(javac::addToolArg);
        } catch (IOException e) {
            throw new Error("traverse dir " + src, e);
        }

        executeTool(javac);
    }

    private static void executeTool(JDKToolLauncher tool) {
        String[] command = tool.getCommand();
        try {
            ProcessTools.executeCommand(command)
                        .shouldHaveExitValue(0);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            throw new Error("execution of " + Arrays.toString(command) + " failed", e);
        }
    }
}
