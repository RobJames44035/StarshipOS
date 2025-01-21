/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8223355
 * @summary Redundant output by javadoc
 * @library /tools/lib ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build toolbox.ToolBox javadoc.tester.*
 * @run main TestGeneratedClasses
 */

import java.nio.file.Path;

import javadoc.tester.JavadocTester;
import toolbox.ToolBox;

public class TestGeneratedClasses extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new TestGeneratedClasses();
        tester.runTests();
    }

    ToolBox tb = new ToolBox();

    @Test
    public void testClasses(Path base) throws Exception {
        Path src = base.resolve("src");
        Path src_m = src.resolve("m");
        tb.writeJavaFiles(src_m,
                "module m { exports p; }",
                "package p; public class C { }");

        javadoc("-d", base.resolve("out").toString(),
                "--source-path", src_m.toString(),
                "-Xdoclint:none",
                "--module", "m");

        // verify that C.html is only generated once
        checkOutput(Output.OUT, true,
                """
                    Building tree for all the packages and classes...
                    Generating testClasses/out/m/p/C.html...
                    Generating testClasses/out/m/p/package-summary.html..."""
                    .replace("/", FS));
    }
}