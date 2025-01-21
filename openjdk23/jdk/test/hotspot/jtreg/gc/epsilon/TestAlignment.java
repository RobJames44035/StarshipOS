/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package gc.epsilon;

/**
 * @test TestAlignment
 * @requires vm.gc.Epsilon
 * @summary Check Epsilon runs fine with (un)usual alignments
 * @bug 8212005
 *
 * @run main/othervm -Xmx64m -XX:+UseTLAB
 *                   -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC
 *                   gc.epsilon.TestAlignment
 *
 * @run main/othervm -Xmx64m -XX:-UseTLAB
 *                   -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC
 *                   gc.epsilon.TestAlignment
 */

/**
 * @test TestAlignment
 * @requires vm.gc.Epsilon
 * @requires vm.bits == "64"
 * @summary Check Epsilon TLAB options with unusual object alignment
 * @bug 8212177
 * @run main/othervm -Xmx64m -XX:+UseTLAB
 *                   -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC
 *                   -XX:ObjectAlignmentInBytes=16
 *                   gc.epsilon.TestAlignment
 *
 * @run main/othervm -Xmx64m -XX:-UseTLAB
 *                   -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC
 *                   -XX:ObjectAlignmentInBytes=16
 *                   gc.epsilon.TestAlignment
 */

public class TestAlignment {
    static Object sink;

    public static void main(String[] args) throws Exception {
        for (int c = 0; c < 1000; c++) {
            sink = new byte[c];
        }
    }
}
