/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug      4924383 8149402 8196202
 * @summary  Test to make sure the -group option works correctly
 *           with the given pattern usages.
 * @library  ../../lib
 * @modules  jdk.javadoc/jdk.javadoc.internal.tool
 * @build    javadoc.tester.*
 * @run main TestGroupOption
 */

import javadoc.tester.JavadocTester;

public class TestGroupOption extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new TestGroupOption();
        tester.runTests();
    }

    @Test
    public void test1() {
        // Make sure the warning is not printed when -group is used correctly.
        javadoc("-d", "out-1",
                "-sourcepath", testSrc,
                "-group", "Package One", "pkg1",
                "-group", "Package Two", "pkg2",
                "-group", "Package Three", "pkg3",
                "pkg1", "pkg2", "pkg3");
        checkExit(Exit.OK);

        checkOutput(Output.OUT, false,
                "-group");
    }

     @Test
    // Make sure the "Other packages" section is printed and the header for empty section is not.
    // Make sure that the headers of group that is defined using patterns are printed.
    public void test2() {
        javadoc("-d", "out-2",
                "-sourcepath", testSrc,
                "-group", "Group pkg*", "pkg*",
                "-group", "Group abc*", "abc*",
                "-group", "Empty group", "qwerty*",
                "-group", "Group a*", "a*",
                "pkg1", "pkg2", "pkg3", "abc1",  "abc2", "abc3", "other", testSrc("InUnnamedPackage.java"));
        checkExit(Exit.OK);

        checkOutput("index.html", true, "Group pkg*", "Group abc*", "Other Packages");
        checkOutput("index.html", false, "Empty group", "Group a*");
    }

    @Test
    public void test3() {
        // Make sure the warning is printed when -group is not used correctly.
        javadoc("-d", "out-3",
                "-sourcepath", testSrc,
                "-group", "Package One", "pkg1",
                "-group", "Package One", "pkg2",
                "-group", "Package One", "pkg3",
                "pkg1", "pkg2", "pkg3");
        checkExit(Exit.OK);

        checkOutput(Output.OUT, true,
                "-group");

    }
}
