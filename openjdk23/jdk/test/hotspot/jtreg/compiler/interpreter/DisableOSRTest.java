/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8159620
 * @summary testing that -XX:-UseOnStackReplacement works with both -XX:(+/-)TieredCompilation
 * @modules java.base/jdk.internal.misc
 * @library /test/lib /
 *
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -XX:+PrintCompilation
 *                   -XX:-BackgroundCompilation -XX:-TieredCompilation -XX:-UseOnStackReplacement
 *                   compiler.interpreter.DisableOSRTest
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -XX:+PrintCompilation
 *                   -XX:-BackgroundCompilation -XX:+TieredCompilation -XX:-UseOnStackReplacement
 *                   compiler.interpreter.DisableOSRTest
 */

package compiler.interpreter;

import jdk.test.whitebox.WhiteBox;

import java.lang.reflect.Method;
import java.util.Random;

public class DisableOSRTest {
    private static final WhiteBox WB = WhiteBox.getWhiteBox();
    private static final Random RANDOM = new Random(42);

    public static int foo() {
        return RANDOM.nextInt();
    }

    public static void main(String[] args) throws Exception {
        Method m = DisableOSRTest.class.getMethod("main", String[].class);

        for (int i = 0; i < 100_000; i++) {
            foo();
        }

        if (WB.isMethodCompiled(m, true /* isOsr */)) {
            throw new RuntimeException("\"" + m + "\" shouldn't be OSR compiled if running with -XX:-UseOnStackReplacement!");
        }
    }
}
