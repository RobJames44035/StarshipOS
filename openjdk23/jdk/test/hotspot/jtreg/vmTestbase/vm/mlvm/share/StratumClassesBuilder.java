/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package vm.mlvm.share;

import jdk.test.lib.JDKToolLauncher;
import jdk.test.lib.Utils;
import jdk.test.lib.process.ProcessTools;
import nsk.share.jdi.sde.InstallSDE;
import vm.mlvm.tools.StratumAP;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class StratumClassesBuilder {
    public static void main(String[] args) {
        Path root = Paths.get(Utils.TEST_ROOT);
        Arrays.stream(args)
              .map(root::resolve)
              .forEach(StratumClassesBuilder::build);
    }

    private static void build(Path file) {
        if (Files.notExists(file)) {
            throw new Error("can't find " + file);
        }
        Path dst = Paths.get("bin")
                        .resolve("classes");
        mkdir(dst);
        compile(file, dst);
        if (file.getFileName().toString().contains("SDE_")) {
            IndifiedClassesBuilder.main(dst.toAbsolutePath().toString());
        }
        addStratum(dst);
    }

    private static void mkdir(Path dst) {
        try {
            Files.createDirectories(dst);
        } catch (IOException e) {
            throw new Error("can't create dir " + dst, e);
        }
    }

    private static void compile(Path file, Path dst) {
        JDKToolLauncher javac = JDKToolLauncher.create("javac")
                                               .addToolArg("-d")
                                               .addToolArg(dst.toString())
                                               .addToolArg("-cp")
                                               .addToolArg(Utils.TEST_CLASS_PATH)
                                               .addToolArg("-processor")
                                               .addToolArg(StratumAP.class.getName())
                                               .addToolArg(file.toAbsolutePath().toString());

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

    private static void addStratum(Path dst) {
        try (Stream<Path> files = Files.walk(dst)) {
            files.map(Path::toAbsolutePath)
                 .filter(p -> p.getFileName().toString().contains("SDE_"))
                 .filter(p -> p.toString().endsWith(".class"))
                 .forEach(p -> {
                     try {
                         InstallSDE.install(
                                 p.toFile(),
                                 classToSmap(p).toFile(),
                                 p.toFile(),
                                 true);
                     } catch (IOException e) {
                         throw new Error("can't install sde for " + p);
                     }
                 });
        } catch (IOException e) {
            throw new Error("can't traverse " + dst, e);
        }
    }

    private static Path classToSmap(Path file) {
        String filename = file.getFileName().toString();
        return file.getParent()
                   .resolve(filename.replaceFirst("\\.class$", ".smap"));
    }
}
