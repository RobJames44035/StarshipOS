/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4706779 4956908
 * @summary  Add text equivalent of class tree ASCII art for accessibility
 * @library ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build javadoc.tester.*
 * @run main AccessAsciiArt
 */

import javadoc.tester.JavadocTester;

public class AccessAsciiArt extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new AccessAsciiArt();
        tester.runTests();
    }

    @Test
    public void test() {
        javadoc("-d", "out",
                "-sourcepath", testSrc,
                "p1", "p1.subpkg");
        checkExit(Exit.OK);

        checkOutput("p1/subpkg/SSC.html", true,
                // Test the top line of the class tree
                """
                    <div class="inheritance"><a href="../C.html" title="class in p1">p1.C</a>""",
                // Test the second line of the class tree
                """
                    <div class="inheritance"><a href="../SC.html" title="class in p1">p1.SC</a>""",
                // Test the third line of the class tree
                """
                    <div class="inheritance">p1.subpkg.SSC</div>
                    </div>
                    </div>""");
    }
}
