/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package vm.runtime.defmeth.shared;

import jdk.test.lib.JDKToolLauncher;
import jdk.test.lib.Utils;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.util.JarUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Build {@code retransform.jar} in current directory using
 * {@code vm/runtime/defmeth/shared/retransform.mf} and classes from
 * {@code vm.runtime.defmeth.shared} package.
 */
public class BuildJar {
    public static void main(String[] args) {
        Path manifest = Paths.get(Utils.TEST_ROOT)
                             .resolve("vmTestbase")
                             .resolve("vm")
                             .resolve("runtime")
                             .resolve("defmeth")
                             .resolve("shared")
                             .resolve("retransform.mf")
                             .toAbsolutePath();
        if (Files.notExists(manifest)) {
            throw new Error("can't find manifest file: " + manifest);
        }

        Path file = foundInClassPath(Util.Transformer.class).toAbsolutePath();
        // Util$Transformer.class is in vm/runtime/defmeth/shared
        Path dir = file.getParent()
                       .getParent()
                       .getParent()
                       .getParent()
                       .getParent()
                       .toAbsolutePath();

        JDKToolLauncher jar = JDKToolLauncher.create("jar")
                                             .addToolArg("cmf")
                                             .addToolArg(manifest.toString())
                                             .addToolArg("retransform.jar")
                                             .addToolArg("-C")
                                             .addToolArg(dir.toString())
                                             .addToolArg(dir.relativize(file).toString());
        String[] command = jar.getCommand();
        try {
            ProcessTools.executeCommand(command)
                        .shouldHaveExitValue(0);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            throw new Error("execution of jar [" + Arrays.toString(command) + "] failed", e);
        }
    }

    private static Path foundInClassPath(Class<?> aClass) {
        Path file = Paths.get(aClass.getName()
                                    .replace(".", File.separator) + ".class");
        for (String dir : Utils.TEST_CLASS_PATH.split(File.pathSeparator)) {
            Path result = Paths.get(dir).resolve(file);
            if (Files.exists(result)) {
                return result;
            }
        }
        throw new Error("can't find " + file + " in " + Utils.TEST_CLASS_PATH);
    }
}

