/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug      8298405
 * @summary  Markdown support in the standard doclet
 * @library  /tools/lib ../../lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 *          jdk.javadoc/jdk.javadoc.internal.tool
 * @build    toolbox.ToolBox javadoc.tester.*
 * @run main TestLineCommentOption
 */

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

import com.sun.source.doctree.DocCommentTree;
import com.sun.source.doctree.DocTree;
import com.sun.source.doctree.RawTextTree;
import com.sun.source.doctree.TextTree;
import com.sun.source.util.DocTreeFactory;
import com.sun.source.util.DocTrees;

import com.sun.tools.javac.api.JavacTrees;

import javadoc.tester.JavadocTester;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.StandardDoclet;
import toolbox.ToolBox;

public class TestLineCommentOption extends JavadocTester {
    public static void main(String... args) throws Exception {
        var tester = new TestLineCommentOption();
        tester.runTests();
    }

    private final ToolBox tb = new ToolBox();


    // This is a somewhat worthless test case, given all the test cases for
    // Markdown content in line comments.
    @Test
    public void testNoOption(Path base) throws Exception {
        // use a dummy option
        // note that we cannot use -Xlint:dangling-doc-comments in javadoc
        test(base, "-XDdummy", "Line comment");
        // in the future, check for diagnostic output about a dangling comment
    }

    @Test
    public void testOption(Path base) throws Exception {
        test(base, "--disable-line-doc-comments", "Traditional comment");
    }

    void test(Path base, String option, String expect) throws Exception {
        Path src = base.resolve("src");
        tb.writeJavaFiles(src, """
            package p;
            /**
             * Traditional comment.
             */
            /// Line comment
            public class C {
                private C() { }
            }""");

        javadoc("-d", base.resolve("api").toString(),
                "--source-path", src.toString(),
                option,
                "p");
        checkExit(Exit.OK);

        checkOutput("p/C.html", true,
                expect);
    }
}
