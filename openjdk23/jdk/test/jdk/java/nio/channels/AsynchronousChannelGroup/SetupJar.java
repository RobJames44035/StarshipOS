/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import jdk.test.lib.util.JarUtils;

public class SetupJar {
    public static void main(String args[]) throws Exception {
        String cp = System.getProperty("test.class.path");
        Path bootlib = Stream.of(cp.split(File.pathSeparator))
                .map(Paths::get)
                .filter(e -> e.endsWith("bootlib"))  // file name
                .findAny()
                .orElseThrow(() -> new InternalError("bootlib not found"));
        JarUtils.createJarFile(Paths.get("privileged.jar"), bootlib);
    }
}
