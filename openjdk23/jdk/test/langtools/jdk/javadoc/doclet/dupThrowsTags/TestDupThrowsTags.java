/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4525364
 * @summary Determine if duplicate throws tags can be used.
 * @library ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build javadoc.tester.*
 * @run main TestDupThrowsTags
 */
import javadoc.tester.JavadocTester;

public class TestDupThrowsTags extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new TestDupThrowsTags();
        tester.runTests();
    }

    @Test
    public void test() {
        javadoc("-d", "out",
                testSrc("TestDupThrowsTags.java"));
        checkExit(Exit.ERROR);

        checkOutput("TestDupThrowsTags.html", true,
                "Test 1 passes",
                "Test 2 passes",
                "Test 3 passes",
                "Test 4 passes");
    }

    /**
     * @throws java.io.IOException Test 1 passes
     * @throws java.io.IOException Test 2 passes
     * @throws java.lang.NullPointerException Test 3 passes
     * @throws java.io.IOException Test 4 passes
     */
    public void method() {}

}
