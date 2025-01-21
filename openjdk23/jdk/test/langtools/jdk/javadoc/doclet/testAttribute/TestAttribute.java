/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8309150
 * @summary Need to escape " inside attribute values
 * @library /tools/lib ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build toolbox.ToolBox javadoc.tester.*
 * @run main TestAttribute
 */

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javadoc.tester.JavadocTester;
import toolbox.ToolBox;

public class TestAttribute extends JavadocTester {

    public final ToolBox tb;
    public static void main(String... args) throws Exception {
        var tester = new TestAttribute();
        tester.runTests();
    }

    public TestAttribute() {
        tb = new ToolBox();
    }

    @Test
    public void testQuote(Path base) throws IOException {
        var src = base.resolve("src");
        tb.writeJavaFiles(src, """
                package p;
                /**
                 * First sentence.
                 * @spec http://example.com title with "quotes"
                 */
                public class C { private C() { } }""");

        javadoc("-d", base.resolve("api").toString(),
                "-sourcepath", src.toString(),
                "p");
        checkExit(Exit.OK);

        // Test some section markers and links to these markers
        checkOutput("p/C.html", true,
                """
                    <a href="http://example.com"><span id="titlewith&quot;quotes&quot;" \
                    class="search-tag-result">title with "quotes"</span></a>""");
    }
}
