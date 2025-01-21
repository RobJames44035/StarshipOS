/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @summary Tests WB::isMethodCompilable(m) in combination with compiler directives that prevent a compilation of m.
 * @bug 8263582
 * @library /test/lib /
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI
 *      -XX:CompileCommand=compileonly,compiler.whitebox.TestMethodCompilableCompilerDirectives::doesNotExist
 *      compiler.whitebox.TestMethodCompilableCompilerDirectives
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI
 *      -XX:CompileCommand=exclude,compiler.whitebox.TestMethodCompilableCompilerDirectives::*
 *      compiler.whitebox.TestMethodCompilableCompilerDirectives
 */

package compiler.whitebox;

import jdk.test.lib.Asserts;
import jdk.test.whitebox.WhiteBox;
import java.lang.reflect.Method;

public class TestMethodCompilableCompilerDirectives {
    private static final WhiteBox WHITE_BOX = WhiteBox.getWhiteBox();

    // Method too simple for C2 and only C1 compiled.
    public static int c1Compiled() {
        return 3;
    }


    // Method first C1 and then C2 compiled.
    public static int c2Compiled() {
        for (int i = 0; i < 100; i++);
        return 3;
    }

    // WB::isMethodCompilable(m) uses Method::is_not_compilable() to decide if m is compilable. Method::is_not_compilable(), however,
    // returns false regardless of any compiler directives if m was not yet tried to be compiled. The compiler directive ExcludeOption
    // to prevent a compilation is evaluated lazily and is only applied when a compilation for m is attempted.
    // Another problem is that Method::is_not_compilable() only returns true for CompLevel_any if C1 AND C2 cannot compile it.
    // This means that a compilation of m must have been attempted for C1 and C2 before WB::isMethodCompilable(m, CompLevel_any) will
    // ever return false. This disregards any compiler directives (e.g. compileonly, exclude) that prohibit a compilation of m completely.
    // WB::isMethodCompilable() should be aware of the ExcludeOption compiler directives at any point in time.
    public static void main(String[] args) throws NoSuchMethodException {
        Method c1CompiledMethod = TestMethodCompilableCompilerDirectives.class.getDeclaredMethod("c1Compiled");
        Method c2CompiledMethod = TestMethodCompilableCompilerDirectives.class.getDeclaredMethod("c2Compiled");

        boolean compilable = WhiteBox.getWhiteBox().isMethodCompilable(c1CompiledMethod);
        Asserts.assertFalse(compilable);
        for (int i = 0; i < 3000; i++) {
            c1Compiled();
        }
        compilable = WhiteBox.getWhiteBox().isMethodCompilable(c1CompiledMethod);
        Asserts.assertFalse(compilable);


        compilable = WhiteBox.getWhiteBox().isMethodCompilable(c2CompiledMethod);
        Asserts.assertFalse(compilable);
        for (int i = 0; i < 3000; i++) {
            c2Compiled();
        }
        compilable = WhiteBox.getWhiteBox().isMethodCompilable(c2CompiledMethod);
        Asserts.assertFalse(compilable);
    }
}
