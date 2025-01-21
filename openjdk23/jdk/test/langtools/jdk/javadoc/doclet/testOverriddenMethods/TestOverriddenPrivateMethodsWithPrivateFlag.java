/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4634891 8026567
 * @summary Determine if overridden methods are properly documented when
 * -protected (default) visibility flag is used.
 * @library ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build javadoc.tester.*
 * @run main TestOverriddenPrivateMethodsWithPrivateFlag
 */

import javadoc.tester.JavadocTester;

public class TestOverriddenPrivateMethodsWithPrivateFlag extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new TestOverriddenPrivateMethodsWithPrivateFlag();
        tester.runTests();
    }

    @Test
    public void test() {
        javadoc("-d", "out",
                "-sourcepath", testSrc,
                "-private",
                "pkg1", "pkg2");
        checkExit(Exit.OK);

        // The public method should be overridden
        checkOutput("pkg1/SubClass.html", true,
                """
                    <dt>Overrides:</dt>
                    <dd><code><a href="BaseClass.html#publicMethod""");

        // The package private method should be overridden since the base and sub class are in the same
        // package.
        checkOutput("pkg1/SubClass.html", true,
                """
                    <dt>Overrides:</dt>
                    <dd><code><a href="BaseClass.html#packagePrivateMethod""");

        // The public method in different package should be overridden
        checkOutput("pkg2/SubClass.html", true,
                """
                    <dt>Overrides:</dt>
                    <dd><code><a href="../pkg1/BaseClass.html#publicMethod""");

        // The private method in should not be overridden
        checkOutput("pkg1/SubClass.html", false,
                """
                    <dt>Overrides:</dt>
                    <dd><code><a href="BaseClass.html#privateMethod""");

        // The private method in different package should not be overridden
        checkOutput("pkg2/SubClass.html", false,
                """
                    <dt>Overrides:</dt>
                    <dd><code><a href="../pkg1/BaseClass.html#privateMethod""");

        // The package private method should not be overridden since the base and sub class are in
        // different packages.
        checkOutput("pkg2/SubClass.html", false,
                """
                    <dt>Overrides:</dt>
                    <dd><code><a href="../pkg1/BaseClass.html#packagePrivateMethod""");
    }
}
