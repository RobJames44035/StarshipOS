/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4524136
 * @summary Test to make sure label is used for inline links.
 * @library ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build javadoc.tester.*
 * @run main TestInlineLinkLabel
 */

import javadoc.tester.JavadocTester;

public class TestInlineLinkLabel extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new TestInlineLinkLabel();
        tester.runTests();
    }

    @Test
    public void test() {
        javadoc("-d", "out",
                "-sourcepath", testSrc,
                "pkg");
        checkExit(Exit.OK);

        checkOutput("pkg/C1.html", true,
                //Search for the label to the package link.
                """
                    <a href="package-summary.html"><code>Here is a link to a package</code></a>""",
                //Search for the label to the class link
                """
                    <a href="C2.html" title="class in pkg"><code>Here is a link to a class</code></a>""");
    }
}
