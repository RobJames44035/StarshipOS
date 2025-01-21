/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test
 * @bug  8222091 8245058
 * @summary  Javadoc does not handle package annotations correctly on package-info.java
 * @library  ../../lib/
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build   javadoc.tester.*
 * @run main TestPackageAnnotation
 */

import javadoc.tester.JavadocTester;

public class TestPackageAnnotation extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new TestPackageAnnotation();
        tester.runTests();
    }

    @Test
    public void testPackageInfoAnnotationNoComment() {
        javadoc("-d", "out-annotation",
                "-sourcepath", testSrc,
                "--no-platform-links",
                "-use",
                "pkg1");
        checkExit(Exit.OK);
        checkOutput("pkg1/package-summary.html", true,
                """
                    <main role="main">
                    <div class="header">
                    <h1 title="Package pkg1" class="title">Package pkg1</h1>
                    </div>
                    <hr>
                    <div class="horizontal-scroll">
                    <div class="package-signature"><span class="annotations">@Deprecated(since="1&lt;2&gt;3")
                    </span>package <span class="element-name">pkg1</span></div>
                    """);
    }

    @Test
    public void testPackageHtmlTag() {
        javadoc("-d", "out-annotation-2",
                "-sourcepath", testSrc,
                "-use",
                "pkg2");
        checkExit(Exit.OK);
        checkOutput("pkg2/package-summary.html", true,
                """
                    <div class="deprecation-block"><span class="deprecated-label">Deprecated.</span>
                    <div class="deprecation-comment">This package is deprecated.</div>
                    </div>
                    <div class="block">This is the description of package pkg2.</div>
                    </section>""");
    }

    @Test
    public void testPackageInfoAndHtml() {
        javadoc("-d", "out-annotation-3",
                "-sourcepath", testSrc,
                "--no-platform-links",
                "-use",
                "pkg3");
        checkExit(Exit.OK);
        checkOutput("pkg3/package-summary.html", true,
                """
                    <main role="main">
                    <div class="header">
                    <h1 title="Package pkg3" class="title">Package pkg3</h1>
                    </div>
                    <hr>
                    <div class="horizontal-scroll">
                    <div class="package-signature"><span class="annotations">@Deprecated(since="1&lt;2&gt;3")
                    </span>package <span class="element-name">pkg3</span></div>
                    """);
    }
}
