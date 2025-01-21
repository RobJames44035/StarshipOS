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
 * @run main TestNestedLinkTag
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

import javadoc.tester.JavadocTester;
import toolbox.ToolBox;

public class TestNestedLinkTag extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new TestNestedLinkTag();
        tester.runTests();
    }

    ToolBox tb = new ToolBox();

    enum TagKind {
        LINK,
        LINKPLAIN;
        final String tag;
        TagKind() {
            tag = name().toLowerCase(Locale.ROOT);
        }
    }

    enum DocLintKind {
        DOCLINT("-Xdoclint"),
        NO_DOCLINT("-Xdoclint:none");
        final String option;
        DocLintKind(String option) {
            this.option = option;
        }
    }

    @Test
    public void testLinkLinkCombo(Path base) throws IOException {
        for (TagKind outer : TagKind.values()) {
            for (TagKind inner : TagKind.values()) {
                for (DocLintKind dl : DocLintKind.values()) {
                    Path dir = Files.createDirectories((base.resolve(outer + "-" + inner + "-" + dl)));
                    testLinkLinkCombo(dir, outer, inner, dl);
                }
            }
        }
    }

    void testLinkLinkCombo(Path base, TagKind outer, TagKind inner, DocLintKind dlk) throws IOException {
        Path src = base.resolve("src");
        tb.writeJavaFiles(src,
                """
                    package p;
                    /** First sentence. {@#OUTER# #m1() ABC {@#INNER# #m2() DEF} GHI} */
                    public class C {
                        /** Comment m1. */
                        public void m1() { }
                        /** Comment m2. */
                        public void m2() { }
                    }
                    """
                    .replaceAll("#OUTER#", outer.tag)
                    .replaceAll("#INNER#", inner.tag));


        javadoc("-d", base.resolve("api").toString(),
                "-sourcepath", src.toString(),
                dlk.option,
                "p");
        checkExit(Exit.OK);

        checkOutput("p/C.html", false,
                "{@link",
                "ABC <a href=\"#m2()\"");
        checkOutput("p/C.html", true,
                "ABC DEF GHI");
        checkOutput(Output.OUT, dlk == DocLintKind.DOCLINT,
                "C.java:2: warning: nested tag: @link");
    }

    @Test
    public void testLinkValue(Path base) throws IOException {
        Path src = base.resolve("src");
        tb.writeJavaFiles(src,
                """
                    package p;
                    /** First sentence. {@link #m1() ABC {@value Short#MAX_VALUE} DEF} */
                    public class C {
                        /** Comment. */
                        public void m1() { }
                    }
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
