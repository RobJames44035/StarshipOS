/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.c2.irTests;

import compiler.lib.ir_framework.*;

/*
 * @test
 * @bug 8295066
 * @summary Test various scalarization scenarios
 * @library /test/lib /
 * @run driver compiler.c2.irTests.TestScalarReplacement
 */
public class TestScalarReplacement {

    public static void main(String[] args) {
        TestFramework.run();
    }
    static class X1 {
        int x = 42;
    }

    @Test
    @IR(failOn = { IRNode.ALLOC })
    public static int test1() {
        X1[] array = new X1[1];
        array[0] = new X1();
        return array[0].x;
    }

    static class X2 {
        int x = 42;
        int y = 43;
        public int hash() { return x + y; }
    }
    static final class ObjectWrapper {
        public Object obj;

        public ObjectWrapper(Object obj) {
            this.obj = obj;
        }
    }

    @Test
    @IR(failOn = { IRNode.ALLOC })
    public static int test2(X2 obj) {
        ObjectWrapper val = new ObjectWrapper(obj);
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++j) {
                val.obj = val.obj;
            }
        }
        return ((X2)val.obj).hash();
    }

    @Run(test = "test2")
    public static void test2_runner() {
        X2 obj = new X2();
        test2(obj);
    }
}

