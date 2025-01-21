/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4671694
 * @summary Test to make sure link to superclass is generated for
 * each class in serialized form page.
 * @library ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build javadoc.tester.*
 * @run main TestSuperClassInSerialForm
 */

import javadoc.tester.JavadocTester;

public class TestSuperClassInSerialForm extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new TestSuperClassInSerialForm();
        tester.runTests();
    }

    @Test
    public void test() {
        javadoc("-d", "out",
                "--no-platform-links",
                "-sourcepath", testSrc,
                "pkg");
        checkExit(Exit.OK);

        checkOutput("serialized-form.html", true,
                """
                    <h3>Class&nbsp;<a href="pkg/SubClass.html" title="class in pkg">pkg.SubClass</a></h3>
                    <div class="type-signature">class SubClass extends <a href="pkg/SuperClass.html" tit\
                    le="class in pkg">SuperClass</a> implements java.io.Serializable</div>""");
    }
}
