/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug      6492694 8026567 8048351 8162363 8183511 8169819 8074407 8196202 8202626 8261976
 * @summary  Test package deprecation.
 * @library  ../../lib/
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build    javadoc.tester.* TestPackageDeprecation
 * @run main TestPackageDeprecation
 */

import javadoc.tester.JavadocTester;

public class TestPackageDeprecation extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new TestPackageDeprecation();
        tester.runTests();
    }

    @Test
    public void testDefault() {
        javadoc("-d", "out-default",
                "-sourcepath", testSrc,
                "-use",
                "pkg", "pkg1", testSrc("C2.java"), testSrc("FooDepr.java"));
        checkExit(Exit.OK);

        checkOutput("pkg1/package-summary.html", true,
                """
                    <div class="deprecation-block"><span class="deprecated-label">Deprecated.</span>
                    <div class="deprecation-comment">This package is Deprecated.</div>"""
        );

        checkOutput("deprecated-list.html", true,
            """
                <li id="contents-package"><a href="#package">Packages</a></li>"""
        );
    }

    @Test
    public void testNoDeprecated() {
        javadoc("-d", "out-nodepr",
                "-sourcepath", testSrc,
                "-use",
                "-nodeprecated",
                "pkg", "pkg1", testSrc("C2.java"), testSrc("FooDepr.java"));
        checkExit(Exit.OK);

        checkOutput("index.html", false,
                "pkg1");
        checkOutput("class-use/C2.ModalExclusionType.html", true,
                """
                    <div class="col-first even-row-color"><a href="#unnamed-package">Unnamed Package</a></div>""");

        checkFiles(false,
                "pkg1/package-summary.html",
                "FooDepr.html");
    }
}
