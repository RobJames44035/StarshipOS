/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package compiler.loopopts;

import java.lang.reflect.Method;
import jdk.test.whitebox.WhiteBox;
import jdk.test.lib.Asserts;
import compiler.whitebox.CompilerWhiteBoxTest;

public class UseCountedLoopSafepoints {
    private static final WhiteBox WB = WhiteBox.getWhiteBox();
    private static final String METHOD_NAME = "testMethod";

    private long accum = 0;

    public static void main (String args[]) throws Exception {
        new UseCountedLoopSafepoints().testMethod();
        Method m = UseCountedLoopSafepoints.class.getDeclaredMethod(METHOD_NAME);
        String directive = "[{ match: \"" + UseCountedLoopSafepoints.class.getName().replace('.', '/')
                + "." + METHOD_NAME + "\", " + "BackgroundCompilation: false }]";
        Asserts.assertTrue(WB.addCompilerDirective(directive) == 1, "Can't add compiler directive");
        Asserts.assertTrue(WB.enqueueMethodForCompilation(m,
                CompilerWhiteBoxTest.COMP_LEVEL_FULL_OPTIMIZATION), "Can't enqueue method");
    }

    private void testMethod() {
        for (int i = 0; i < 100; i++) {
            accum += accum << 5 + accum >> 4 - accum >>> 5;
        }
    }
}
