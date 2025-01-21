/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug      8257925
 * @summary  enable more support for nested inline tags
 * @library  /tools/lib ../../lib
 * @modules  jdk.javadoc/jdk.javadoc.internal.tool
 * @build    javadoc.tester.*
 * @run main TestNestedSummaryTag
 */

import java.io.IOException;
import java.nio.file.Path;

import javadoc.tester.JavadocTester;
import toolbox.ToolBox;

public class TestNestedSummaryTag extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new TestNestedSummaryTag();
        tester.runTests();
    }

    ToolBox tb = new ToolBox();

    enum DocLintKind {
        DOCLINT("-Xdoclint"),
        NO_DOCLINT("-Xdoclint:none");
        final String option;
        DocLintKind(String option) {
            this.option = option;
        }
    }

    @Test
    public void testSummarySummaryDocLint(Path base) throws IOException {
        testSummarySummary(base, DocLintKind.DOCLINT);
    }

    @Test
    public void testSummarySummaryNoDocLint(Path base) throws IOException {
        testSummarySummary(base, DocLintKind.NO_DOCLINT);
    }

    void testSummarySummary(Path base, DocLintKind dlk) throws IOException {
        Path src = base.resolve("src");
        tb.writeJavaFiles(src,
                """
                    package p;
                    /** {@summary abc ABC {@summary def DEF} GHI} */
                    public class C { }
                    """);
        javadoc("-d", base.resolve("api").toString(),
                "-sourcepath", src.toString(),
                dlk.option,
                "p");
        checkExit(Exit.OK);

        checkOutput("p/C.html", false,
                "{@summary");
        checkOutput(Output.OUT, dlk == DocLintKind.DOCLINT,
                "C.java:2: warning: nested tag: @summary");
    }

    @Test
    public void testSummaryValue(Path base) throws IOException {
        Path src = base.resolve("src");
        tb.writeJavaFiles(src,
                """
                    package p;
                    /** {@summary abc ABC {@value Short#MAX_VALUE} DEF} */
                    public class C { }
                    """);
        javadoc("-d", base.resolve("api").toString(),
                "-sourcepath", src.toString(),
                "--no-platform-links",
                "p");
        checkExit(Exit.OK);

        checkOutput("p/C.html", false,
                "{@value");
        checkOutput("p/C.html", true,
                "ABC 32767 DEF");
    }
}
