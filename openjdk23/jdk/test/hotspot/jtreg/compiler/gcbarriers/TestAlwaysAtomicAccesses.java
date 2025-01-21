/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test TestAlwaysAtomicAccesses
 * @bug 8285301
 * @summary Test memory accesses from compiled code with AlwaysAtomicAccesses.
 * @run main/othervm -Xcomp -XX:+UnlockExperimentalVMOptions -XX:+AlwaysAtomicAccesses
 *                   compiler.membars.TestAlwaysAtomicAccesses
 */

package compiler.membars;

public class TestAlwaysAtomicAccesses {

    public static void main(String[] args) {
        // Nothing to do here. Compilations are triggered by -Xcomp.
        System.out.println("Test passed");
    }
}
