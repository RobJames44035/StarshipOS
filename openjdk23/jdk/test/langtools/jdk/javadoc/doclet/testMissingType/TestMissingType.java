/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug      8173804
 * @summary  make sure doclet can handle missing types
 * @library  ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build    javadoc.tester.*
 * @run main TestMissingType
 */

import javadoc.tester.JavadocTester;

public class TestMissingType extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new TestMissingType();
        tester.runTests();
    }

    @Test
    public void test() {
        javadoc("-d", "out",
                "-use",
                "-sourcepath", testSrc,
                "p");
        checkExit(Exit.ERROR);
        checkOutput(Output.STDERR, false,
                "java.lang.UnsupportedOperationException: should not happen");
    }
}
