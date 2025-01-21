/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug      8261203
 * @summary  Incorrectly escaped javadoc html with type annotations
 * @library  /tools/lib ../../lib/
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build toolbox.ToolBox javadoc.tester.*
 * @run main TestMethodId
 */

import java.io.IOException;
import java.nio.file.Path;

import javadoc.tester.JavadocTester;
import toolbox.ToolBox;

public class TestMethodId extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new TestMethodId();
        tester.runTests();
    }

    private ToolBox tb = new ToolBox();

    @Test
    public void testMethodId(Path base) throws IOException {
        Path src = base.resolve("src");
        tb.writeJavaFiles(src,
                """
                    package p;
                    public class C {
                        public void m(@A("anno-text") int i) { }
                    }
                    """,
                """
                    package p;
                    public @interface A {
                        String value();
                    }
                    """);

        javadoc("-d", base.resolve("out").toString(),
                "-Xdoclint:none",
                "--source-path", src.toString(),
                "p");
        checkExit(Exit.OK);

        checkOutput("p/C.html",
                true,
                """
                    <code><a href="#m(int)" class="member-name-link">m</a><wbr>(int&nbsp;i)</code>""",
                """
                    <section class="detail" id="m(int)">
                    <h3>m</h3>""");
    }
}