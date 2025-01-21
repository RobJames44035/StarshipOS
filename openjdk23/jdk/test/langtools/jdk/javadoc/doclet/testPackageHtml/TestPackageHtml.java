/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug      8150096 8179704
 * @summary  test package.html handling
 * @library  ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build    javadoc.tester.*
 * @run main TestPackageHtml
 */

import javadoc.tester.JavadocTester;

public class TestPackageHtml extends JavadocTester {
    public static void main(String... args) throws Exception  {
        var tester = new TestPackageHtml();
        tester.runTests();
    }

    // Make sure package.html is recognized by doclint
    @Test
    public void testPackageHtml() {
        javadoc("-d", "out-pkg-html-1",
                "-sourcepath", testSrc,
                "pkg1");
        checkExit(Exit.ERROR);
        checkOutput(Output.OUT, true, "package.html:4: error: malformed HTML");
    }

    // Doclet must handle empty body in package.html, must
    // ignore html comment in the first sentence and must
    // ignore trailing whitespace in a first sentence.
    @Test
    public void testPackageHtmlWithEmptyBody() {
        javadoc("-d", "out-pkg-html-2",
                "-sourcepath", testSrc,
                "pkg2", "pkg3", "pkg4");
        checkExit(Exit.OK);
        checkOutput("index-all.html", true,
              """
                  <dl class="index">
                  <dt><a href="pkg2/package-summary.html">pkg2</a> - package pkg2</dt>
                  <dt><a href="pkg3/package-summary.html">pkg3</a> - package pkg3</dt>
                  <dd>
                  <div class="block">This is a documentation for <a href="pkg3/package-summary.html"><code>pkg3</code></a></div>
                  </dd>
                  <dt><a href="pkg4/package-summary.html">pkg4</a> - package pkg4</dt>
                  <dd>
                  <div class="block">This is a documentation for <a href="pkg4/package-summary.html"><code>pkg4</code></a></div>
                  </dd>
                  </dl>
                  """);
    }
}
