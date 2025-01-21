/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4369014 4851991 8164407 8205593
 * @summary Determine if the docRoot inline tag works properly.
 * If docRoot performs as documented, the test passes.
 * Make sure that the docRoot tag works with the -bottom option.
 * @library ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build javadoc.tester.*
 * @run main TestDocRootInlineTag
 */

import javadoc.tester.JavadocTester;

public class TestDocRootInlineTag extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new TestDocRootInlineTag();
        tester.runTests();
    }

    @Test
    public void test() {
        String uri = "http://www.java.sun.com/j2se/1.4/docs/api";

        javadoc("-bottom", """
            The value of @docRoot is "{@docRoot}\"""",
                "-d", "out",
                "-source", "8",
                "-sourcepath", testSrc,
                "-linkoffline", uri, testSrc,
                testSrc("TestDocRootTag.java"), "pkg");
        checkExit(Exit.OK);

        checkOutput("TestDocRootTag.html", true,
                "<a href=\"" + uri + """
                    /java/io/File.html" title="class or interface in java.io" class="external-link"><code>File</code></a>""",
                """
                    <a href="./index-all.html">index</a>""",
                "<a href=\"" + uri + """
                    /java/io/File.html" title="class or interface in java.io" class="external-link"><code>Second File Link</code></a>""",
                "The value of @docRoot is \"./\"");

        checkOutput("index-all.html", true,
                """
                    My package page is <a href="./pkg/package-summary.html">here</a>""");
    }
}
