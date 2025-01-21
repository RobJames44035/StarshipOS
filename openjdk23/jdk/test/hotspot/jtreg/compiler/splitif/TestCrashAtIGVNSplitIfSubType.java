/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8303279
 * @summary C2: crash in SubTypeCheckNode::sub() at IGVN split if
 * @run main/othervm -XX:-TieredCompilation -XX:-BackgroundCompilation -XX:+UnlockDiagnosticVMOptions -XX:+StressIGVN -XX:StressSeed=598200189 TestCrashAtIGVNSplitIfSubType
 * @run main/othervm -XX:-TieredCompilation -XX:-BackgroundCompilation -XX:+UnlockDiagnosticVMOptions -XX:+StressIGVN TestCrashAtIGVNSplitIfSubType
 */

public class TestCrashAtIGVNSplitIfSubType {
    private static volatile int barrier;

    public static void main(String[] args) {
        A a = new A();
        B b = new B();
        for (int i = 0; i < 20_000; i++) {
            test(a);
            test(b);
            testHelper1(null, 0);
        }
    }

    private static void test(Object o) {
        int i = 2;
        for (; i < 4; i *= 2) {

        }
        o = testHelper1(o, i);
        if (o instanceof A) {
            barrier = 0x42;
        }
    }

    private static Object testHelper1(Object o, int i) {
        if (i < 3) {
            o = null;
        } else {
            if (o == null) {
            }
        }
        if (i < 2) {
            barrier = 42;
        }
        return o;
    }

    private static class A {
    }

    private static class B {
    }
}
