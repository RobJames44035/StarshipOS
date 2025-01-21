/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug      8174839 8175200 8186332
 * @summary  Bad overriding method should not crash
 * @library  ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build    javadoc.tester.*
 * @run main TestBadOverride
 */

import javadoc.tester.JavadocTester;

public class TestBadOverride extends JavadocTester {

    /**
     * The entry point of the test.
     * @param args the array of command line arguments.
     */
    public static void main(String... args) throws Exception {
        var tester = new TestBadOverride();
        tester.runTests();
    }

    @Test
    public void test() {
        javadoc("-d", "out",
                "-sourcepath", testSrc,
                "pkg4");
        // explicitly configure "no crash" check, which is the main interest of this test
        setAutomaticCheckNoStacktrace(true);
    }
}
