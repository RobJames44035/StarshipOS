/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug      6758050 8025633 8182765 8345664
 * @summary  Test HTML output for nested generic types.
 * @library  ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build    javadoc.tester.*
 * @run main TestNestedGenerics
 */

import javadoc.tester.JavadocTester;

public class TestNestedGenerics extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new TestNestedGenerics();
        tester.runTests();
    }

    @Test
    public void test() {
        javadoc("-d", "out",
                "-sourcepath", testSrc,
                "pkg");
        checkExit(Exit.OK);

        checkOutput("pkg/NestedGenerics.html", true,
            """
                    <div class="block">Contains <a href="#foo(java.util.Map)"><code>foo(Map)</code></a></div>""");
    }
}
