/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug      5008230
 * @summary  Check the outer class when documenting enclosing class/interface.
 * @library  ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build    javadoc.tester.*
 * @run main TestEnclosingClass
 */

import javadoc.tester.JavadocTester;

public class TestEnclosingClass extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new TestEnclosingClass();
        tester.runTests();
    }

    @Test
    public void test() {
        javadoc("-d", "out",
                "-sourcepath", testSrc,
                "pkg");
        checkExit(Exit.OK);

        checkOutput("pkg/MyClass.MyInterface.html", true,
                "Enclosing class:");
    }
}
