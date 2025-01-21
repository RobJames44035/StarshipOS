/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8189782 8210555
 * @summary Test for isSupportedOption
 * @modules java.compiler
 *          jdk.compiler
 * @run main IsSupportedOptionTest
 */

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

/**
 * Tests for JavaCompiler.isSupportedOption method.
 */
public class IsSupportedOptionTest {
    public static void main(String... args) throws Exception {
        new IsSupportedOptionTest().run();
    }

    public void run() throws Exception {
        JavaCompiler tool = ToolProvider.getSystemJavaCompiler();
        check(tool, "-source", 1);
        check(tool, "--source", 1);
        check(tool, "-target", 1);
        check(tool, "--target", 1);
        check(tool, "--add-modules", 1);
        check(tool, "-verbose", 0);
        check(tool, "-proc:none", 0);
        check(tool, "-Xlint", 0);
        check(tool, "-Xlint:unchecked", 0);
        check(tool, "-Xdoclint", 0);
        check(tool, "-Xdoclint:stats", 0);
        check(tool, "-Xdoclint/package:foo", 0);
        check(tool, "--debug=any", 1);
        check(tool, "-g", 0);
        check(tool, "-g:vars", 0);
        check(tool, "-g:none", 0);
        check(tool, "-ZZZ", -1);
        check(tool, "-Afoobar", 0);

        try {
            check(tool, null, -1);
            throw new AssertionError("null was accepted without exception");
        } catch (NullPointerException e) {
        }
    }

    private void check(JavaCompiler tool, String option, int numArgs) {
        System.err.println("check " + option);
        int n = tool.isSupportedOption(option);
        if (n != numArgs) {
            throw new AssertionError("unexpected result for option: " + option + ": " + n);
        }
    }
}

