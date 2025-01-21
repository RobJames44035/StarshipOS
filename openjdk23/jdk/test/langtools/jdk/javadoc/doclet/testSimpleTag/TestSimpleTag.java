/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4695326 4750173 4920381 8078320 8071982 8239804
 * @summary Test the declaration of simple tags using -tag. Verify that
 * "-tag name" is a shortcut for "-tag name:a:Name:".  Also verity that
 * you can escape the ":" character with a back slash so that it is not
 * considered a separator when parsing the simple tag argument.
 * @library ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build javadoc.tester.*
 * @run main TestSimpleTag
 */

import javadoc.tester.JavadocTester;

public class TestSimpleTag extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new TestSimpleTag();
        tester.runTests();
    }

    @Test
    public void test() {
        javadoc("-d", "out",
                "-sourcepath", testSrc,
                "-tag", "param",
                "-tag", "todo",
                "-tag", "ejb\\:bean:a:EJB Beans:",
                "-tag", "regular:a:Regular Tag:",
                "-tag", "tag-with-hyphens:a:Tag-With-Hyphens:",
                testSrc("C.java"));
        checkExit(Exit.OK);

        checkOutput("C.html", true,
                "<dl class=\"notes\">",
                "<dt>Todo:</dt>",
                "<dt>EJB Beans:</dt>",
                "<dt>Regular Tag:</dt>",
                "<dt>Tag-With-Hyphens:</dt>",
                """
                    <dt>Parameters:</dt>
                    <dd><code>arg</code> - this is an int argument.</dd>""");
    }
}
