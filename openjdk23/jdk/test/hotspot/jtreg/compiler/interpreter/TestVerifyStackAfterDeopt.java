/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */


/*
 * @test TestVerifyStackAfterDeopt
 * @bug 8148871
 * @bug 8209825
 * @summary Checks VerifyStack after deoptimization of array allocation slow call
 * @run main/othervm -XX:+IgnoreUnrecognizedVMOptions -XX:TieredStopAtLevel=1
 *                   -XX:AllocatePrefetchLines=1 -XX:AllocateInstancePrefetchLines=1 -XX:AllocatePrefetchStepSize=16 -XX:AllocatePrefetchDistance=1
 *                   -XX:MinTLABSize=1k -XX:TLABSize=1k
 *                   -XX:+DeoptimizeALot -XX:+VerifyStack
 *                   compiler.interpreter.TestVerifyStackAfterDeopt
 */

package compiler.interpreter;

public class TestVerifyStackAfterDeopt {

    private long method(long l1, long l2, Object[] a) {
        return l1 + l2;
    }

    private long result[] = new long[1];

    private void test() {
        // For the array allocation, C1 emits a slow call into the runtime
        // that deoptimizes the caller frame due to -XX:+DeoptimizeALot.
        // The VerifyStack code then gets confused because the following
        // bytecode instruction is an invoke and the interpreter oop map
        // generator reports the oop map after execution of that invoke.
        this.result[0] = method(1L, 2L, new Object[0]);
    }

    public static void main(String[] args) {
        TestVerifyStackAfterDeopt t = new TestVerifyStackAfterDeopt();
        // Run long enough for C1 compilation to trigger and TLAB to fill up
        for (int i = 0; i < 100_000; ++i) {
            t.test();
        }
    }
}
