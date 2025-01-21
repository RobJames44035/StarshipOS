/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8240975 8281335
 * @modules java.base/jdk.internal.loader
 * @build java.base/* p.Test Main
 * @run main/othervm/native -Xcheck:jni Main
 * @summary Test loading and unloading of native libraries
 */

import jdk.internal.loader.NativeLibrariesTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String... args) throws Exception {
        setup();

        // Verify a native library from test.nativepath
        NativeLibrariesTest test = new NativeLibrariesTest();
        test.runTest();

        // System::loadLibrary succeeds even the library is loaded as raw library
        System.loadLibrary(NativeLibrariesTest.LIB_NAME);

        // expect NativeLibraries to succeed even the library has been loaded by System::loadLibrary
        test.loadTestLibrary();

        // unload all NativeLibrary instances
        test.unload();

        // load zip library from JDK
        test.load(System.mapLibraryName("zip"), true /* succeed */);

        // load non-existent library
        test.load(System.mapLibraryName("NotExist"), false /* fail to load */);
    }
    /*
     * move p/Test.class out from classpath to the scratch directory
     */
    static void setup() throws IOException {
        String dir = System.getProperty("test.classes", ".");
        Path p = Files.createDirectories(Paths.get("classes").resolve("p"));
        Files.move(Paths.get(dir, "p", "Test.class"), p.resolve("Test.class"));
    }

}
