/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package gc.g1.unloading;

import jdk.test.lib.Utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Uses {@link gc.g1.unloading.GenClassPoolJar} to build {@code classPool.jar}
 * in current directory.
 */
public class GenClassesBuilder {
    public static void main(String[] args) {
        Path template = Paths.get(Utils.TEST_ROOT)
                             .resolve("vmTestbase")
                             .resolve("gc")
                             .resolve("g1")
                             .resolve("unloading")
                             .resolve("ClassNNN.java.template")
                             .toAbsolutePath();
        Path dir = Paths.get(".").toAbsolutePath();
        String count = "1000";
        if (Files.notExists(template)) {
            throw new Error("can't find template file: " + template);
        }
        try {
            GenClassPoolJar.main(new String[]{template.toString(), dir.toString(), count});
        } catch (Exception e) {
            throw new Error("can't generate classPool.jar", e);
        }
    }
}
