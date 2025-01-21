/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug      4904038
 * @summary  The field detail comment should not show up in the output if there
 *           are no fields to document.
 * @library  ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build    javadoc.tester.*
 * @run main TestHtmlComments
 */

import javadoc.tester.JavadocTester;

public class TestHtmlComments extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new TestHtmlComments();
        tester.runTests();
    }

    @Test
    public void run() {
        javadoc("-d", "out",
                "-sourcepath", testSrc,
                testSrc("C.java"));
        checkExit(Exit.OK);

        checkOutput("C.html", false,
            "<!-- ============ FIELD DETAIL =========== -->");
    }
}
