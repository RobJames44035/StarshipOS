/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug      8298405
 * @summary  Markdown support in the standard doclet
 * @library  /tools/lib ../../lib
 * @modules  jdk.javadoc/jdk.javadoc.internal.tool
 * @build    toolbox.ToolBox javadoc.tester.*
 * @run main TestMarkdownInheritDoc
 */

import javadoc.tester.JavadocTester;
import toolbox.ToolBox;

import java.nio.file.Path;
import java.util.List;

public class TestMarkdownInheritDoc extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new TestMarkdownInheritDoc();
        tester.runTests();
    }

    ToolBox tb = new ToolBox();

    @Test
    public void testInherit_md_md(Path base) throws Exception {
        Path src = base.resolve("src");
        tb.writeJavaFiles(src,
                """
                    package p;
                    public class Base {
                        /// Markdown comment.
                        /// @throws Exception Base _Markdown_
                        public void m() throws Exception { }
                    }""",
                """
                    package p;
                    public class Derived extends Base {
                        /// Markdown comment.
                        /// @throws {@inheritDoc}
                        public void m() throws Exception { }
                    }
                    """);
        javadoc("-d", base.resolve("api").toString(),
                "-Xdoclint:none",
                "--no-platform-links",
                "--source-path", src.toString(),
                "p");

        checkOutput("p/Derived.html", true,
                """
                    <dt>Throws:</dt>
                    <dd><code>java.lang.Exception</code> - Base <em>Markdown</em></dd>
                    """);
    }

    @Test
    public void testInherit_md_plain(Path base) throws Exception {
        Path src = base.resolve("src");
        tb.writeJavaFiles(src,
                """
                    package p;
                    public class Base {
                        /// Markdown comment.
                        /// @throws Exception Base _Markdown_
                        public void m() throws Exception { }
                    }""",
                """
                    package p;
                    public class Derived extends Base {
                        /**
                         * Plain comment.
                         * @throws {@inheritDoc}
                         */
                         public void m() throws Exception { }
                    }
                    """);
        javadoc("-d", base.resolve("api").toString(),
                "-Xdoclint:none",
                "--no-platform-links",
                "--source-path", src.toString(),
                "p");

        checkOutput("p/Derived.html", true,
                """
                    <dt>Throws:</dt>
                    <dd><code>java.lang.Exception</code> - Base <em>Markdown</em></dd>
                    """);
    }

    @Test
    public void testInherit_plain_md(Path base) throws Exception {
        Path src = base.resolve("src");
        tb.writeJavaFiles(src,
                """
                    package p;
                    public class Base {
                        /**
                         * Plain comment.
                         * @throws Exception Base _Not Markdown_
                         */
                         public void m() throws Exception { }
                    }""",
                """
                    package p;
                    public class Derived extends Base {
                        /// Markdown comment.
                        /// @throws {@inheritDoc}
                        public void m() throws Exception { }
                    }
                    """);
        javadoc("-d", base.resolve("api").toString(),
                "-Xdoclint:none",
                "--no-platform-links",
                "--source-path", src.toString(),
                "p");

        checkOutput("p/Derived.html", true,
                """
                    <dt>Throws:</dt>
                    <dd><code>java.lang.Exception</code> - Base _Not Markdown_</dd>
                    """);
    }
}
