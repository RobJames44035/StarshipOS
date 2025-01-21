/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * @test
 * @key stress randomness
 * @bug 7179138 8271341
 * @summary Incorrect result with String concatenation optimization
 *
 * @run main/othervm -Xbatch -XX:+IgnoreUnrecognizedVMOptions -XX:-TieredCompilation
 *      compiler.c2.Test7179138_1
 * @run main/othervm -Xbatch -XX:+IgnoreUnrecognizedVMOptions -XX:-TieredCompilation
 *      -XX:+UnlockDiagnosticVMOptions -XX:+StressIGVN compiler.c2.Test7179138_1
 * @run main/othervm -Xbatch -XX:+IgnoreUnrecognizedVMOptions -XX:-TieredCompilation
 *      -XX:+UnlockDiagnosticVMOptions -XX:+StressIGVN -XX:+AlwaysIncrementalInline
 *      compiler.c2.Test7179138_1
 *
 * @author Skip Balk
 */

package compiler.c2;

public class Test7179138_1 {
    public static void main(String[] args) throws Exception {
        System.out.println("Java Version: " + System.getProperty("java.vm.version"));
        long[] durations = new long[60];
        for (int i = 0; i < 100000; i++) {
            // this empty for-loop is required to reproduce this bug
            for (long duration : durations) {
                // do nothing
            }
            {
                String s = "test";
                int len = s.length();

                s = new StringBuilder(String.valueOf(s)).append(s).toString();
                len = len + len;

                s = new StringBuilder(String.valueOf(s)).append(s).toString();
                len = len + len;

                s = new StringBuilder(String.valueOf(s)).append(s).toString();
                len = len + len;

                if (s.length() != len) {
                    System.out.println("Failed at iteration: " + i);
                    System.out.println("Length mismatch: " + s.length() + " <> " + len);
                    System.out.println("Expected: \"" + "test" + "test" + "test" + "test" + "test" + "test" + "test" + "test" + "\"");
                    System.out.println("Actual:   \"" + s + "\"");
                    System.exit(97);
                }
            }
        }
    }
}
