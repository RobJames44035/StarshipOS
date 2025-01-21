/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @library /test/lib
 * @modules java.base/jdk.internal.misc:+open
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:+IgnoreUnrecognizedVMOptions
 *                   -Xbootclasspath/a:. -XX:+WhiteBoxAPI
 *                   -Xbatch -XX:-TieredCompilation -XX:+AlwaysIncrementalInline
 *                   -XX:CompileCommand=compileonly,compiler.ciReplay.TestDumpReplay::*
 *                   compiler.ciReplay.TestDumpReplay
 */

package compiler.ciReplay;

import jdk.test.whitebox.WhiteBox;

public class TestDumpReplay {
    private static final WhiteBox WHITE_BOX = WhiteBox.getWhiteBox();

    private static final String emptyString;

    static {
        emptyString = "";
    }

    public static void m1() {
        m2();
    }

    public static void m2() {
        m3();
    }

    public static void m3() {

    }

    public static void main(String[] args) {
        // Add compiler control directive to force generation of replay file
        String directive = "[{ match: \"*.*\", DumpReplay: true }]";
        if (WHITE_BOX.addCompilerDirective(directive) != 1) {
            throw new RuntimeException("Failed to add compiler directive");
        }

        // Trigger compilation of m1
        for (int i = 0; i < 10_000; ++i) {
            m1();
        }
    }
}
