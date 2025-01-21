/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4638136 7198273 8025633 8081854 8182765 8258659 8261976
 * @summary  Add ability to skip over nav bar for accessibility
 * @library ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build javadoc.tester.*
 * @run main AccessSkipNav
 */

import javadoc.tester.JavadocTester;

public class AccessSkipNav extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new AccessSkipNav();
        tester.runTests();
    }

    @Test
    public void test() {
        javadoc("-d", "out",
                "-sourcepath", testSrc,
                "p1", "p2");
        checkExit(Exit.OK);

        // Testing only for the presence of the <a href> and <a id>
        checkOutput("p1/C1.html", true,
                // Top navbar <a href>
                """
                    <a href="#skip-navbar-top" title="Skip navigation links">Skip navigation links</a>""",
                // Top navbar <span id>
                """
                    <span class="skip-nav" id="skip-navbar-top"></span>""");
    }
}
