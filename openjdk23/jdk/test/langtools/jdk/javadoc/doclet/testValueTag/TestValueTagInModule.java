/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8210244 8261976
 * @summary {@value} should be permitted in module documentation
 * @library /tools/lib ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 *          jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 * @build javadoc.tester.*
 * @run main TestValueTagInModule
 */


import java.nio.file.Path;
import java.nio.file.Paths;

import toolbox.ModuleBuilder;
import toolbox.ToolBox;

import javadoc.tester.JavadocTester;

public class TestValueTagInModule extends JavadocTester {

    final ToolBox tb;

    public static void main(String... args) throws Exception {
        var tester = new TestValueTagInModule();
        tester.runTests();
    }

    TestValueTagInModule() {
        tb = new ToolBox();
    }

    @Test
    public void test(Path base) throws Exception {
        Path srcDir = base.resolve("src");
        createTestClass(srcDir);

        Path outDir = base.resolve("out");
        javadoc("-d", outDir.toString(),
                "--module-source-path", srcDir.toString(),
                "--module", "m1");

        checkExit(Exit.OK);

        checkOutput("m1/module-summary.html", true,
                """
                    <section class="module-description" id="module-description">
                    <!-- ============ MODULE DESCRIPTION =========== -->
                    <div class="block">value of field CONS : <a href="pkg/A.html#CONS">100</a></div>""");
    }

    void createTestClass(Path srcDir) throws Exception {
        new ModuleBuilder(tb, "m1")
                .comment("value of field CONS : {@value pkg.A#CONS}")
                .exports("pkg")
                .classes("package pkg; public class A{ public static final int CONS = 100;}")
                .write(srcDir);
    }
}
