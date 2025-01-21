/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/**
 * @test
 * @bug 8073956
 * @summary Tests C2 EA with allocated object escaping through a call.
 *
 * @run main/othervm
 *      -XX:CompileCommand=dontinline,compiler.escapeAnalysis.TestEscapeThroughInvoke::create
 *      compiler.escapeAnalysis.TestEscapeThroughInvoke
 */

package compiler.escapeAnalysis;

public class TestEscapeThroughInvoke {
    private A a;

    public static void main(String[] args) {
        TestEscapeThroughInvoke test = new TestEscapeThroughInvoke();
        test.a = new A(42);
        // Make sure run gets compiled by C2
        for (int i = 0; i < 100_000; ++i) {
            test.run();
        }
    }

    private void run() {
        // Allocate something to trigger EA
        new Object();
        // Create a new escaping instance of A and
        // verify that it is always equal to 'a.saved'.
        A escapingA = create(42);
        a.check(escapingA);
    }

    // Create and return a new instance of A that escaped through 'A::saveInto'.
    // The 'dummy' parameters are needed to avoid EA skipping the methods.
    private A create(Integer dummy) {
        A result = new A(dummy);
        result.saveInto(a, dummy); // result escapes into 'a' here
        return result;
    }

    static class A {
        private A saved;

        public A(Integer dummy) {
        }

        public void saveInto(A other, Integer dummy) {
            other.saved = this;
        }

        public void check(A other) {
            if (this.saved != other) {
                throw new RuntimeException("TEST FAILED: Objects not equal.");
            }
        }
    }
}
