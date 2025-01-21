/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8027977
 * @summary Test to verify javadoc executes without CompletionFailure exception.
 * @library ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build javadoc.tester.*
 * @run main TestCompletionFailure
 */

import javadoc.tester.JavadocTester;

public class TestCompletionFailure extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new TestCompletionFailure();
        tester.runTests();
    }

    @Test
    public void test() {
        javadoc("-d", "out",
                "-sourcepath", testSrc,
                "pkg1");
        checkExit(Exit.OK);

        checkOutput(Output.STDERR, false,
                "sun.util.locale.provider.LocaleProviderAdapter");
    }
}
