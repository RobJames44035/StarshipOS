/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test
 * @bug 8227766
 * @run main/othervm -XX:+IgnoreUnrecognizedVMOptions -XX:+CheckUnhandledOops -Xmx100m TestOutOfMemory
 */

public class TestOutOfMemory {
    public static void main(java.lang.String[] unused) {
        final int BIG = 0x100000;
        // Getting OOM breaks the unhandled oop detector
        try {
            int[][] X = new int[BIG][];
            for (int i = 0; i < BIG; i++) {
                X[i] = new int[BIG];
                System.out.println("length = " + X.length);
            }
         } catch (OutOfMemoryError oom) {
            System.out.println("OOM expected");
         }
    }
}
