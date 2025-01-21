/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug      8322708
 * @summary  Test to make sure global tags work properly
 * @library  /tools/lib ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build    toolbox.ToolBox javadoc.tester.*
 * @run main TestGlobalHtml
 */

import javadoc.tester.JavadocTester;
import toolbox.ToolBox;

import java.nio.file.Path;

public class TestGlobalHtml extends JavadocTester {
    ToolBox tb = new ToolBox();

    public static void main(String... args) throws Exception {
        var tester = new TestGlobalHtml();
        tester.runTests();
    }

    @Test
    public void testGlobalTags() {
        javadoc("--allow-script-in-comments",
                "-d",
                "out-global",
                "-sourcepath",
                testSrc,
                "pkg1");
        checkExit(Exit.OK);
    }

    @Test
    public void testNegative(Path base) throws Exception {
        Path src = base.resolve("src");
        tb.writeJavaFiles(src,
                """
                package p;
                /**
                 * class comment
                 * <a href="https://openjdk.org/">Hyperlink to the OpenJDK website</a>
                 */
                public class C {
                    /**
                     * <form>
                     *   <label for="methodname">Method name:</label><br>
                     *   <input type="text" id="methodname" name="methodname"><br>
                     *   <label for="paramname">Method Parameter:</label><br>
                     *   <input type="text" id="paramname" name="paramname">
                     * </form>
                     */
                    public C() {
                    }
                }
                """);

        javadoc("--allow-script-in-comments",
                "-d",
                "out-negative",
                "-sourcepath",
                src.toString(),
                "p");
        checkExit(Exit.ERROR);
    }
}
