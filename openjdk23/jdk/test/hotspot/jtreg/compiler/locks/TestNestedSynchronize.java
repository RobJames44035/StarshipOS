/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/**
 * @test
 * @bug 8322996
 * @summary Ensure no assert error in C2 with deeply nested synchronize
 *          statements.
 * @run main/othervm -XX:CompileCommand=compileonly,compiler.locks.TestNestedSynchronize::test
 *                   -Xcomp
 *                   compiler.locks.TestNestedSynchronize
 */

package compiler.locks;

public class TestNestedSynchronize {

    public static void main(String[] args) {
        test();
    }

    public static void test() {

        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {
        synchronized (TestNestedSynchronize.class) {

        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
        }
    }
}
