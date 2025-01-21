/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8243396
 * @summary general tests for command-line help
 * @library ../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build javadoc.tester.*
 * @run main CommandLineHelpTest
 */

import java.nio.file.Path;

import javadoc.tester.JavadocTester;

public class CommandLineHelpTest extends JavadocTester {
    public static void main(String... args) throws Exception {
        var tester = new CommandLineHelpTest();
        tester.runTests();
    }

    @Test
    public void testStandard(Path base) {
        javadoc("-d", base.resolve("out").toString(),
                "--help");
        checkExit(Exit.OK);

        // check no resources missing
        checkOutput(Output.OUT, false,
                "message file broken");

        checkOutput(Output.OUT, true,
                "@<file>",
                "-J<flag>");
    }

}
