/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug      8288545
 * @summary  Missing space in error message
 * @library  /tools/lib ../../lib
 * @modules  jdk.javadoc/jdk.javadoc.internal.tool
 * @build    toolbox.ToolBox javadoc.tester.*
 * @run main TestLinkNotFound
 */

import javadoc.tester.JavadocTester;
import toolbox.ToolBox;

import java.nio.file.Path;

public class TestLinkNotFound extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new TestLinkNotFound();
        tester.runTests();
    }

    ToolBox tb = new ToolBox();

    @Test
    public void test(Path base) throws Exception {
        Path src = base.resolve("src");
        tb.writeJavaFiles(src,
                """
                    /**
                     * Comment.
                     * {@link nowhere label}
                     */
                    public class C{ }
                    """);

        javadoc("-d", base.resolve("api").toString(),
                "-Xdoclint:none",
                "-Werror",
                "-sourcepath", src.toString(),
                src.resolve("C.java").toString());
        checkExit(Exit.ERROR);

        // the use of '\n' in the following check implies that the label does not appear after the reference
        checkOutput(Output.OUT, true,
                "reference not found: nowhere\n");
    }
}
