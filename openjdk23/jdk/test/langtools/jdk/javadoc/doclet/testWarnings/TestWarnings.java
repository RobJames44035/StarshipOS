/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug      4515705 4804296 4702454 4697036 8025633 8182765 8247235
 * @summary  Make sure that first sentence warning only appears once.
 *           Make sure that only warnings/errors are printed when quiet is used.
 *           Make sure that links to private/unincluded methods do not cause
 *           a "link unresolved" warning.
 *           Make sure error message starts with "error -".
 * @library  ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build    javadoc.tester.*
 * @run main TestWarnings
 */

import javadoc.tester.JavadocTester;

public class TestWarnings extends JavadocTester {
    public static void main(String... args) throws Exception  {
        var tester = new TestWarnings();
        tester.runTests();
    }

    @Test
    public void testDefault() {
        javadoc("-d", "out-default",
                "-sourcepath", testSrc,
                "pkg");
        checkExit(Exit.ERROR);

        checkOutput(Output.OUT, false,
                "X.java:23: error: self-closing element not allowed");

        checkOutput(Output.OUT, false,
                "X.java:24: error: self-closing element not allowed");

        checkOutput(Output.OUT, false,
                "X.java:25: error: self-closing element not allowed");

        checkOutput(Output.OUT, false,
                "X.java:26: error: self-closing element not allowed");

        checkOutput(Output.OUT, true,
                "X.java:28: error: self-closing element not allowed");

        checkOutput(Output.OUT, true,
                "X.java:28: warning: empty <p> tag");

        checkOutput("pkg/X.html", false,
                "can't find m()");
        checkOutput("pkg/X.html", false,
                "can't find X()");
        checkOutput("pkg/X.html", false,
                "can't find f");
    }

    @Test
    public void testPrivate() {
        javadoc("-d", "out-private",
                "-private",
                "-sourcepath", testSrc,
                "pkg");
        checkExit(Exit.ERROR);

        checkOutput("pkg/X.html", true,
            """
                <a href="#m()"><code>m()</code></a><br/>""",
            """
                <a href="#%3Cinit%3E()"><code>X()</code></a><br/>""",
            """
                <a href="#f"><code>f</code></a><br/>""");
    }
}
