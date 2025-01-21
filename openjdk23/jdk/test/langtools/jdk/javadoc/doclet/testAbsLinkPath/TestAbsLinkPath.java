/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4640745
 * @summary This test verifys that the -link option handles absolute paths.
 * @library ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build javadoc.tester.*
 * @run main TestAbsLinkPath
 */

import javadoc.tester.JavadocTester;

public class TestAbsLinkPath extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new TestAbsLinkPath();
        tester.runTests();
    }

    @Test
    public void test1() {
        String out1 = "out1";
        javadoc("-d", out1, "-sourcepath", testSrc, "pkg2");
        checkExit(Exit.OK);

        javadoc("-d", "out2",
                "-sourcepath", testSrc,
                "-link", "../" + out1,
                "pkg1");
        checkExit(Exit.OK);

        checkOutput("pkg1/C1.html", true,
                "C2.html");
    }
}
