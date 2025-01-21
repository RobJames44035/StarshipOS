/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4638723 8015882 8176131 8176331
 * @summary Test to ensure that the refactored version of the standard
 * doclet still works with Taglets that implement the 1.4.0 interface.
 * @library ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build javadoc.tester.* ToDoTaglet UnderlineTaglet Check
 * @run main TestLegacyTaglet
 */

import javadoc.tester.JavadocTester;

public class TestLegacyTaglet extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new TestLegacyTaglet();
        tester.runTests();
    }

    @Test
    public void test() {
        javadoc("-d", "out",
                "-sourcepath", testSrc,
                "-tagletpath", System.getProperty("test.classes", "."),
                "-taglet", "ToDoTaglet",
                "-taglet", "Check",
                "-taglet", "UnderlineTaglet",
                testSrc("C.java"));
        checkExit(Exit.OK);
        checkOutput("C.html", true,
                "This is an <u>underline</u>",
                """
                    <DT><B>To Do:</B><DD><table summary="Summary" cellpadding=2 cellspacing=0><tr><t\
                    d bgcolor="yellow">Finish this class.</td></tr></table></DD>""",
                """
                    <DT><B>To Do:</B><DD><table summary="Summary" cellpadding=2 cellspacing=0><tr><t\
                    d bgcolor="yellow">Tag in Method.</td></tr></table></DD>""");
        checkOutput(Output.STDERR, false,
                "NullPointerException");
    }
}
