/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * LoadLibraryUnloadTest ensures all objects (NativeLibrary) are deallocated
 * when loaded in concurrent mode.
 */
/*
 * @test
 * @bug 8266310 8289919 8293282
 * @summary Checks that JNI_OnLoad is invoked only once when multiple threads
 *          call System.loadLibrary concurrently, and JNI_OnUnload is invoked
 *          when the native library is loaded from a custom class loader.
 * @library /test/lib
 * @build LoadLibraryUnload p.Class1
 * @run main/othervm/native LoadLibraryUnloadTest
 */

import jdk.test.lib.Asserts;
import jdk.test.lib.process.OutputAnalyzer;

import static jdk.test.lib.process.ProcessTools.*;

public class LoadLibraryUnloadTest {

    private static String testClassPath = System.getProperty("test.classes");
    private static String testLibraryPath = System.getProperty("test.nativepath");

    private final static long countLines(OutputAnalyzer output, String string) {
        return output.asLines()
                     .stream()
                     .filter(s -> s.contains(string))
                     .count();
    }

    private final static void dump(OutputAnalyzer output) {
        output.asLines()
              .stream()
              .forEach(s -> System.out.println(s));
    }

    public static void main(String[] args) throws Throwable {

        OutputAnalyzer outputAnalyzer = executeCommand(createTestJavaProcessBuilder(
                "-Dtest.classes=" + testClassPath,
                "-Djava.library.path=" + testLibraryPath,
                "LoadLibraryUnload"));
        dump(outputAnalyzer);

        Asserts.assertTrue(
                countLines(outputAnalyzer, "Native library loaded from Class1.") == 2,
                "Native library expected to be loaded in 2 threads.");

        long refCount = countLines(outputAnalyzer, "Native library loaded.");

        Asserts.assertTrue(refCount > 0, "Failed to load native library.");

        System.out.println("Native library loaded in " + refCount + " threads");

        Asserts.assertTrue(refCount == 1, "Native library is loaded more than once.");

        Asserts.assertTrue(
                countLines(outputAnalyzer, "Native library unloaded.") == refCount,
                "Failed to unload native library");

        Asserts.assertEquals(0, outputAnalyzer.getExitValue(),
                "LoadLibraryUnload exit value not zero");
    }
}
