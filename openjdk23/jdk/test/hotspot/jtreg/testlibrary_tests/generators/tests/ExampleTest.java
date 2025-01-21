/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @summary An example test that shows how to use the Generators library.
 * @modules java.base/jdk.internal.misc
 * @library /test/lib /
 * @run driver testlibrary_tests.generators.tests.ExampleTest
 */

package testlibrary_tests.generators.tests;

import compiler.lib.generators.Generator;

import static compiler.lib.generators.Generators.G;


public class ExampleTest {
    static class FakeException extends RuntimeException {}

    static class UnderTest {
        private enum State { STAND_BY, FIRST, SECOND };

        private State state = State.STAND_BY;

        void doIt(int x) {
            state = switch (state) {
                case State.STAND_BY -> x == (1 << 10) + 3 ? State.FIRST : State.STAND_BY;
                case State.FIRST -> x == (1 << 5) - 2 ? State.SECOND : State.STAND_BY;
                case State.SECOND -> {
                    if (x == (1 << 4)) throw new FakeException();
                    yield State.STAND_BY;
                }
            };
        }
    }

    public static void main(String[] args) {
        // This test should print "Assertion triggered by special" (see the math below) but almost never
        // "Assertion triggered by uniform" as the chance of triggering is about 2^-96.
        try {
            test(G.uniformInts());
        } catch (FakeException e) {
            System.out.println("Assertion triggered by uniform");
        }
        try {
            // 408 ints => 1/408 * 1/408 * 1/408 => 1/67_917_312 => with 70_000_000 loop iterations we should trigger
            test(G.powerOfTwoInts(3));
        } catch (FakeException e) {
            System.out.println("Assertion triggered by special");
        }
    }

    public static void test(Generator<Integer> g) {
        UnderTest underTest = new UnderTest();
        for (int i = 0; i < 70_000_000 * 3; i++) {
            underTest.doIt(g.next());
        }
    }
}
