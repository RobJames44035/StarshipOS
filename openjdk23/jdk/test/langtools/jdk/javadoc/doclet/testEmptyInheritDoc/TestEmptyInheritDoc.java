/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug      8267219
 * @summary  The method summary's description cell should not be removed when
 *           it only has inline tags that don't produce any output
 * @library  /tools/lib ../../lib
 * @modules  jdk.javadoc/jdk.javadoc.internal.tool
 * @build    javadoc.tester.*
 * @run main TestEmptyInheritDoc
 */

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javadoc.tester.JavadocTester;
import toolbox.ToolBox;

public class TestEmptyInheritDoc extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new TestEmptyInheritDoc();
        tester.runTests();
    }

    ToolBox tb = new ToolBox();
    Path src;

    TestEmptyInheritDoc() throws Exception {
        src = Files.createDirectories(Paths.get("src"));
        tb.writeJavaFiles(src,
                """
                    package pkg;

                    public class First {
                        public void act() {}
                    }
                    """,
                """
                    package pkg;

                    public class Second extends First {
                        /**
                         * {@inheritDoc}
                         */
                        public void act() {}
                        public void bark() {}
                    }
                    """);
    }

    @Test
    public void run() {
        javadoc("-d", "out-empty-inheritdoc",
                "-sourcepath", src.toString(),
                "pkg");
        checkExit(Exit.OK);

        checkOutput("pkg/Second.html", true,
                """
                   <div class="col-second even-row-color method-summary-table \
                   method-summary-table-tab2 method-summary-table-tab4"><code>\
                   <a href="#act()" class="member-name-link">act</a>()</code></div>
                   <div class="col-last even-row-color method-summary-table \
                   method-summary-table-tab2 method-summary-table-tab4"></div>""");
    }
}
