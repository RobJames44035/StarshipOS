/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug      4475679
 * @summary  Verify that packages.html is no longer generated since it is no
 *           longer used.
 * @library  ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build    javadoc.tester.*
 * @run main TestNoPackagesFile
 */

import javadoc.tester.JavadocTester;

public class TestNoPackagesFile extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new TestNoPackagesFile();
        tester.runTests();
    }

    @Test
    public void test() {
        javadoc("-d", "out",
                "-sourcepath", testSrc,
                testSrc("C.java"));
        checkExit(Exit.OK);

        // packages.html file should not be generated anymore.
        checkFiles(false, "packages.html");
    }
}
