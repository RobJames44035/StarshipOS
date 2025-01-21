/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4645058 4747738 4855054 8024756 8141492 8196202 8205593 8215599
 * @summary  Javascript IE load error when linked by -linkoffline
 *           Window title shouldn't change when loading left frames (javascript)
 * @library ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build javadoc.tester.*
 * @run main JavascriptWinTitle
 */

import javadoc.tester.JavadocTester;

public class JavascriptWinTitle extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new JavascriptWinTitle();
        tester.runTests();
    }

    @Test
    public void test() {
        javadoc("-d", "out",
                "-source", "8",
                "-doctitle", "Document Title",
                "-windowtitle", "Window Title",
                "-overview", testSrc("overview.html"),
                "-linkoffline", "http://java.sun.com/j2se/1.4/docs/api", testSrc,
                "-sourcepath", testSrc,
                "p1", "p2");
        checkExit(Exit.OK);
        checkOutput("index.html", true,
                "<script type=\"text/javascript\">",
                """
                    <body class="package-index-page">""");

        // Test that "onload" is not present in BODY tag:
        checkOutput("p1/package-summary.html", true, """
            <body class="package-declaration-page">""");

        checkOutput("p1/C.html", true, "<title>C (Window Title)</title>");
    }
}
