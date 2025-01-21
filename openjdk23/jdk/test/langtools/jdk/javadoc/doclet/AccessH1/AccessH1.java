/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4636667 7052425 8016549 8196202
 * @summary  Use <H1, <H2>, and <H3> in proper sequence for accessibility
 * @library ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build javadoc.tester.*
 * @run main AccessH1
 */


import javadoc.tester.JavadocTester;

public class AccessH1 extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new AccessH1();
        tester.runTests();
    }

    @Test
    public void test() {
        javadoc("-d", "out",
                "-doctitle", "Document Title",
                "-sourcepath", testSrc,
                "p1", "p2");
        checkExit(Exit.OK);

        // Test the style sheet
        checkOutput("resource-files/stylesheet.css", true,
                """
                    h1 {
                        font-size:1.428em;
                    }""");

        // Test the doc title in the overview page
        checkOutput("index.html", true,
                """
                    <h1 class="title">Document Title</h1>""");
    }
}
