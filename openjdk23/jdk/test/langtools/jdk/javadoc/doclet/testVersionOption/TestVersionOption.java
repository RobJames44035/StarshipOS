/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug      8177048
 * @summary  javadoc should support --version and --full-version flags
 * @library ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build    javadoc.tester.* TestVersionOption
 * @run main TestVersionOption
 */

import javadoc.tester.JavadocTester;

public class TestVersionOption extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new TestVersionOption();
        tester.runTests();
    }

    @Test
    public void testFullVersionOption() {
        javadoc("--full-version");
        checkExit(Exit.OK);

        checkOutput(Output.OUT, true, "javadoc full version \"" + System.getProperty("java.runtime.version") + "\"");
    }


    @Test
    public void testVersionOption() {
        javadoc("--version");
        checkExit(Exit.OK);

        checkOutput(Output.OUT, true, "javadoc " + System.getProperty("java.version"));
    }

}
