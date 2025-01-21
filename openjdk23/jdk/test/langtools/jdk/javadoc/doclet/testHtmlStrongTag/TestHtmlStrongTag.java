/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 6786028 8026567
 * @summary This test verifies the use of <strong> HTML tag instead of <B> by Javadoc std doclet.
 * @library ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build javadoc.tester.*
 * @run main TestHtmlStrongTag
 */

import javadoc.tester.JavadocTester;

public class TestHtmlStrongTag extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new TestHtmlStrongTag();
        tester.runTests();
    }

    @Test
    public void test1() {
        javadoc("-d", "out-1",
                "-sourcepath", testSrc,
                "pkg1");
        checkExit(Exit.OK);

        checkOutput("pkg1/C1.html", true,
            """
                <dl class="notes">
                <dt>See Also:</dt>""");

        checkOutput("pkg1/C1.html", false,
            "<STRONG>Method Summary</STRONG>",
            "<B>");

        checkOutput("pkg1/package-summary.html", false,
            "<STRONG>Class Summary</STRONG>");
    }

    @Test
    public void test2() {
        javadoc("-d", "out-2",
                "-sourcepath", testSrc,
                "pkg2");
        checkExit(Exit.OK);

        checkOutput("pkg2/C2.html", true,
                "<B>Comments:</B>");

        checkOutput("pkg2/C2.html", false,
                "<STRONG>Method Summary</STRONG>");
    }
}
