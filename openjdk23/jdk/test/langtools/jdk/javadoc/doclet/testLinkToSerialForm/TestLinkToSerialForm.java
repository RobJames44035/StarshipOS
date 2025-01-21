/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4521661 8081854 8182765
 * @summary Test to make sure that there is a link with a proper anchor
 * from a serializable class to serialized-form.html.
 * @library ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build javadoc.tester.*
 * @run main TestLinkToSerialForm
 */

import javadoc.tester.JavadocTester;

public class TestLinkToSerialForm extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new TestLinkToSerialForm();
        tester.runTests();
    }

    @Test
    public void test() {
        javadoc("-d", "out",
                "-sourcepath", testSrc,
                "pkg");
        checkExit(Exit.OK);

        checkOutput("serialized-form.html", true,
                """
                    <section class="serialized-class-details" id="pkg.C">""");
        checkOutput("pkg/C.html", true,
                """
                    <a href="../serialized-form.html#pkg.C">""");
    }
}
