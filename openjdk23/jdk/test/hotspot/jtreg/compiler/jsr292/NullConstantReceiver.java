/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test
 * @bug 8059556 8158639 8164508
 *
 * @run main/othervm -Xbatch compiler.jsr292.NullConstantReceiver
 * @run main/othervm -Xbatch -XX:CompileCommand=exclude,*::run compiler.jsr292.NullConstantReceiver
 * @run main/othervm -Xbatch -XX:CompileCommand=compileonly,*::run compiler.jsr292.NullConstantReceiver
 */

package compiler.jsr292;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class NullConstantReceiver {
    static final MethodHandle target;
    static {
        try {
            target = MethodHandles.lookup().findVirtual(NullConstantReceiver.class, "test", MethodType.methodType(void.class));
        } catch (ReflectiveOperationException e) {
            throw new Error(e);
        }
    }

    public void test() {}

    static void run() throws Throwable {
        target.invokeExact((NullConstantReceiver) null);
    }

    public static void main(String[] args) throws Throwable {
        for (int i = 0; i<15000; i++) {
            try {
                run();
            } catch (NullPointerException e) {
                // expected
                continue;
            }
            throw new AssertionError("NPE wasn't thrown");
        }
        System.out.println("TEST PASSED");
    }
}
