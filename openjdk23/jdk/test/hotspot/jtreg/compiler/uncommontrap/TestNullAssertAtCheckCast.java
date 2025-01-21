/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8257594
 * @summary Test that failing checkcast does not trigger repeated recompilation until cutoff is hit.
 * @requires vm.compiler2.enabled
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI
 *                   -Xbatch -XX:CompileCommand=dontinline,compiler.uncommontrap.TestNullAssertAtCheckCast::test*
 *                   -XX:CompileCommand=inline,compiler.uncommontrap.TestNullAssertAtCheckCast::cast
 *                   -XX:CompileCommand=inline,compiler.uncommontrap.TestNullAssertAtCheckCast::store
 *                   compiler.uncommontrap.TestNullAssertAtCheckCast
 */

package compiler.uncommontrap;

import jdk.test.whitebox.WhiteBox;

import java.lang.reflect.Method;

public class TestNullAssertAtCheckCast {
    private static final WhiteBox WB = WhiteBox.getWhiteBox();
    private static final int COMP_LEVEL_FULL_OPTIMIZATION = 4;

    static Long cast(Object val) {
        return (Long)val;
    }

    static void test1() {
        try {
            // Always fails
            cast(new Integer(42));
        } catch (ClassCastException cce) {
            // Ignored
        }
    }

    static void test2(Integer val) {
        try {
            // Always fails
            cast(val);
        } catch (ClassCastException cce) {
            // Ignored
        }
    }

    static void store(Object[] array, Object val) {
        array[0] = val;
    }

    static void test3() {
        try {
            // Always fails
            store(new Long[1], new Integer(42));
        } catch (ArrayStoreException cce) {
            // Ignored
        }
    }

    static void test4(Integer val) {
        try {
            // Always fails
            store(new Long[1], val);
        } catch (ArrayStoreException cce) {
            // Ignored
        }
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 1_000_000; ++i) {
            test1();
            test2((i % 2 == 0) ? null : 42);
            test3();
            test4((i % 2 == 0) ? null : 42);
        }
        Method method = TestNullAssertAtCheckCast.class.getDeclaredMethod("test1");
        if (!WB.isMethodCompilable(method, COMP_LEVEL_FULL_OPTIMIZATION, false)) {
            throw new RuntimeException("TestNullAssertAtCheckCast::test1 not compilable");
        }
        method = TestNullAssertAtCheckCast.class.getDeclaredMethod("test2", Integer.class);
        if (!WB.isMethodCompilable(method, COMP_LEVEL_FULL_OPTIMIZATION, false)) {
            throw new RuntimeException("TestNullAssertAtCheckCast::test2 not compilable");
        }
        method = TestNullAssertAtCheckCast.class.getDeclaredMethod("test3");
        if (!WB.isMethodCompilable(method, COMP_LEVEL_FULL_OPTIMIZATION, false)) {
            throw new RuntimeException("TestNullAssertAtCheckCast::test3 not compilable");
        }
        method = TestNullAssertAtCheckCast.class.getDeclaredMethod("test4", Integer.class);
        if (!WB.isMethodCompilable(method, COMP_LEVEL_FULL_OPTIMIZATION, false)) {
            throw new RuntimeException("TestNullAssertAtCheckCast::test4 not compilable");
        }
    }
}

