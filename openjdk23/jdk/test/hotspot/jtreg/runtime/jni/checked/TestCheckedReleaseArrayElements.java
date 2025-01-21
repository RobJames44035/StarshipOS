/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/* @test
 * @bug 8258077
 * @summary verify multiple release calls on a copied array work when checked
 * @requires vm.flagless
 * @library /test/lib
 * @run main/native TestCheckedReleaseArrayElements launch
 */

import java.util.Arrays;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.Utils;
import jtreg.SkippedException;

public class TestCheckedReleaseArrayElements {

    static {
        System.loadLibrary("TestCheckedReleaseArrayElements");
    }

    public static void main(String[] args) throws Throwable {
        if (args == null || args.length == 0) {
            test();
        } else {
            // Uses executeProcess() instead of executeTestJava() to avoid passing options
            // that might generate output on stderr (which should be empty for this test).
            ProcessBuilder pb =
                ProcessTools.createLimitedTestJavaProcessBuilder("-Xcheck:jni",
                                                                 "--enable-native-access=ALL-UNNAMED",
                                                                 "-Djava.library.path=" + Utils.TEST_NATIVE_PATH,
                                                                 "TestCheckedReleaseArrayElements");
            OutputAnalyzer output = ProcessTools.executeProcess(pb);
            output.shouldHaveExitValue(0);
            output.stderrShouldBeEmpty();
            output.stdoutShouldNotBeEmpty();
        }
    }

    /*
     * If GetIntArrayElements returns a copy, we update the array in slices
     * calling ReleaseIntArrayElements with JNI_COMMIT to write-back the
     * updates, which are then checked. Finally we use JNI_ABORT to free
     * the copy.
     */
    public static void test() {
        final int slices = 3;
        final int sliceLength = 3;
        int[] arr = new int[slices * sliceLength];

        if (!init(arr)) {
            throw new SkippedException("Test skipped as GetIntArrayElements did not make a copy");
        }

        System.out.println("Array before: " + Arrays.toString(arr));

        // We fill the array in slices so that arr[i] = i
        for (int i = 0; i < slices; i++) {
            int start = i * sliceLength;
            fill(arr, start, sliceLength);
            System.out.println("Array during: " + Arrays.toString(arr));
            check(arr, (i + 1) * sliceLength);
        }
        System.out.println("Array after: " + Arrays.toString(arr));
        cleanup(arr);
    }

    /*
     * Calls GetIntArrayElements and stashes the native pointer for
     * use by fill() if a copy was made.
     * Returns true if a copy was made else false.
    */
    static native boolean init(int[] arr);

    /*
     * Fills in target[start] to target[start+count-1], so that
     * target[i] == i. The update is done natively using the raw
     * pointer into the array.
     */
    static native void fill(int[] target, int start, int count);

    /*
     * Properly release the copied array
     */
    static native void cleanup(int[] target);


    static void check(int[] source, int count) {
        for (int i = 0; i < count; i++) {
            if (source[i] != i) {
                System.out.println("Failing source array: " + Arrays.toString(source));
                throw new RuntimeException("Expected source[" + i + "] == " +
                                           i + " but got " + source[i]);
            }
        }
        for (int i = count; i < source.length; i++) {
            if (source[i] != 0) {
                System.out.println("Failing source array: " + Arrays.toString(source));
                throw new RuntimeException("Expected source[" + i +
                                           "] == 0 but got " + source[i]);
            }
        }

    }
}
