/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4625883 8180019
 * @summary Make sure that bad -link arguments trigger errors.
 * @library ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build javadoc.tester.*
 * @run main TestBadLinkOption
 */

import javadoc.tester.JavadocTester;

public class TestBadLinkOption extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new TestBadLinkOption();
        tester.runTests();
    }

    @Test
    public void test() {
        String out = "out";
        javadoc("-d", out,
                "-sourcepath", testSrc,
                "-link", "a-non-existent-link",
                "pkg");
        checkExit(Exit.ERROR);

        checkOutput(Output.OUT, true,
                "Error reading file:");
    }
}
