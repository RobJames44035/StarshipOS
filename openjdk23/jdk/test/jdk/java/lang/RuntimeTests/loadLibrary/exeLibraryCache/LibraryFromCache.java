/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/**
 * @test
 * @bug 8275703
 * @library /test/lib
 * @requires os.family == "mac"
 * @run main/native/othervm -Djava.library.path=/usr/lib LibraryFromCache blas
 * @run main/native/othervm -Djava.library.path=/usr/lib LibraryFromCache BLAS
 * @summary Test System::loadLibrary to be able to load a library even
 *          if it's not present on the filesystem on macOS which supports
 *          dynamic library cache
 */

import jdk.test.lib.process.OutputAnalyzer;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LibraryFromCache {
    public static void main(String[] args) throws IOException {
        System.out.println("os.version = " + System.getProperty("os.version"));

        String libname = args[0];
        if (!systemHasLibrary(libname)) {
            System.out.println("Test skipped. Library " + libname + " not found");
            return;
        }

        System.loadLibrary(libname);
    }

    /*
     * Returns true if dlopen successfully loads the specified library
     */
    private static boolean systemHasLibrary(String libname) throws IOException {
        Path launcher = Paths.get(System.getProperty("test.nativepath"), "LibraryCache");
        ProcessBuilder pb = new ProcessBuilder(launcher.toString(), "lib" + libname + ".dylib");
        OutputAnalyzer outputAnalyzer = new OutputAnalyzer(pb.start());
        System.out.println(outputAnalyzer.getOutput());
        return outputAnalyzer.getExitValue() == 0;
    }
}
