/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/**
 * @test
 * @bug 8287223
 * @library /test/lib / patches
 *
 * @build java.base/java.lang.invoke.MethodHandleHelper
 * @run main/bootclasspath/othervm -Xbatch -XX:CompileCommand=compileonly,*::test -XX:-TieredCompilation                         compiler.jsr292.NullConstantMHReceiver
 * @run main/bootclasspath/othervm -Xbatch -XX:CompileCommand=compileonly,*::test -XX:+TieredCompilation -XX:TieredStopAtLevel=1 compiler.jsr292.NullConstantMHReceiver
 */

package compiler.jsr292;

import java.lang.invoke.MethodHandleHelper;

public class NullConstantMHReceiver {
    static void test() throws Throwable {
        MethodHandleHelper.invokeBasicL(null);
    }

    public static void main(String[] args) throws Throwable {
        for (int i = 0; i < 15000; i++) {
            try {
                test();
            } catch (NullPointerException e) {
                // expected
                continue;
            }
            throw new AssertionError("NPE wasn't thrown");
        }
        System.out.println("TEST PASSED");
    }
}
