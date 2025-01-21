/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8176901
 * @summary The doclet should cope with bad HTML form
 * @library ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build javadoc.tester.*
 * @run main TestBadHtml
 */

import javadoc.tester.JavadocTester;

public class TestBadHtml extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new TestBadHtml();
        tester.runTests();
    }

    @Test
    public void testNegative() {
        javadoc("-d", "out1",
                "-sourcepath", testSrc,
                "pkg1");

        checkExit(Exit.ERROR);

        checkOutput(Output.STDERR, false, "NullPointerException");
        checkOutput(Output.OUT, false, "NullPointerException");
    }
}
