/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test
 * @bug 8219313
 * @summary Support module specific stylesheets
 * @library /tools/lib ../../lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 *          jdk.javadoc/jdk.javadoc.internal.tool
 * @build javadoc.tester.* toolbox.ToolBox
 * @run main TestModuleSpecificStylesheet
 */


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import toolbox.ModuleBuilder;
import toolbox.ToolBox;

import javadoc.tester.JavadocTester;

public class TestModuleSpecificStylesheet extends JavadocTester {

    final ToolBox tb;

    public static void main(String... args) throws Exception {
        var tester = new TestModuleSpecificStylesheet();
        tester.runTests();
    }

    TestModuleSpecificStylesheet() {
        tb = new ToolBox();
    }

    @Test
    public void test(Path base) throws Exception {
        Path srcDir = base.resolve("src");
        Path outDir = base.resolve("out");

        new ModuleBuilder(tb, "ma")
                .classes("package pa; public class A{}")
                .classes("package pa.pb; public class B{}")
                .exports("pa")
                .exports("pa.pb")
                .write(srcDir);

        Path docFilesDir = Files.createDirectories(srcDir.resolve("ma").resolve("doc-files"));
        Path stylesheet = docFilesDir.resolve("spanstyle.css");
        Files.createFile(stylesheet);
        Files.write(stylesheet, List.of("span{ color:blue; }"));

        javadoc("-d", outDir.toString(),
                "--module-source-path", srcDir.toString(),
                "--module", "ma");

        checkExit(Exit.OK);

        checkOutput("ma/module-summary.html", true,
                """
                    <link rel="stylesheet" type="text/css" href="../ma/doc-files/spanstyle.css" title="Style">""");

        checkOutput("ma/pa/package-summary.html", true,
                """
                    <link rel="stylesheet" type="text/css" href="../../ma/doc-files/spanstyle.css" title="Style">""");

        checkOutput("ma/pa/A.html", true,
                """
                    <link rel="stylesheet" type="text/css" href="../../ma/doc-files/spanstyle.css" title="Style">""");

        checkOutput("ma/pa/pb/B.html", true,
                """
                    <link rel="stylesheet" type="text/css" href="../../../ma/doc-files/spanstyle.css" title="Style">""");

        checkOutput("ma/pa/pb/package-summary.html", true,
                """
                    <link rel="stylesheet" type="text/css" href="../../../ma/doc-files/spanstyle.css" title="Style">""");
    }
}
