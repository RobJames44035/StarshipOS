/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4714257 8164407 8205593
 * @summary Test to make sure that the title attribute shows up in links.
 * @library ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build javadoc.tester.*
 * @run main TestTitleInHref
 */

import javadoc.tester.JavadocTester;

public class TestTitleInHref extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new TestTitleInHref();
        tester.runTests();
    }

    @Test
    public void test() {
        String uri = "http://java.sun.com/j2se/1.4/docs/api";
        javadoc("-d", "out",
                "-source", "8",
                "-sourcepath", testSrc,
                "-linkoffline", uri, testSrc,
                "pkg");
        checkExit(Exit.OK);

        checkOutput("pkg/Links.html", true,
                //Test to make sure that the title shows up in a class link.
                """
                    <a href="Class.html" title="class in pkg">""",
                //Test to make sure that the title shows up in an interface link.
                """
                    <a href="Interface.html" title="interface in pkg">""",
                //Test to make sure that the title shows up in cross link shows up
                "<a href=\"" + uri + """
                    /java/io/File.html" title="class or interface in java.io" class="external-link">\
                    <code>This is a cross link to class File</code></a>""");
    }
}
