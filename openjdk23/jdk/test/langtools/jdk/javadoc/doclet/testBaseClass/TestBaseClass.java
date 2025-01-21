/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4197513
 * @summary Javadoc does not process base class.
 * @library ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build BaseClass
 * @build javadoc.tester.*
 * @run main TestBaseClass
 */

import javadoc.tester.JavadocTester;

public class TestBaseClass extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new TestBaseClass();
        tester.runTests();
    }

    @Test
    public void test() {
        javadoc("-sourcepath", testSrc,
                "-docletpath", System.getProperty("test.classes", "."),
                "-doclet", "BaseClass",
                testSrc("Bar.java"), "baz");
        checkExit(Exit.OK);
    }
}
