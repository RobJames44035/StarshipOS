/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * @test
 * @bug 8145013
 * @summary Javac doesn't report warnings/errors if module provides unexported service and doesn't use it itself
 * @library /tools/lib
 * @modules
 *      jdk.compiler/com.sun.tools.javac.api
 *      jdk.compiler/com.sun.tools.javac.main
 * @build toolbox.ToolBox toolbox.JavacTask ModuleTestBase
 * @run main ServiceProvidedButNotExportedOrUsedTest
 */

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import toolbox.JavacTask;
import toolbox.Task;
import toolbox.ToolBox;

public class ServiceProvidedButNotExportedOrUsedTest extends ModuleTestBase {
    public static void main(String... args) throws Exception {
        ServiceProvidedButNotExportedOrUsedTest t = new ServiceProvidedButNotExportedOrUsedTest();
        t.runTests();
    }

    @Test
    public void testWarning(Path base) throws Exception {
        Path src = base.resolve("src");
        tb.writeJavaFiles(src,
                "module m { provides p1.C1 with p2.C2; }",
                "package p1; public class C1 { }",
                "package p2; public class C2 extends p1.C1 { }");
        Path classes = base.resolve("classes");
        Files.createDirectories(classes);

        List<String> output = new JavacTask(tb)
                .outdir(classes)
                .options("-Werror", "-XDrawDiagnostics")
                .files(findJavaFiles(src))
                .run(Task.Expect.FAIL)
                .writeAll()
                .getOutputLines(Task.OutputKind.DIRECT);
        List<String> expected = Arrays.asList(
                "module-info.java:1:12: compiler.warn.service.provided.but.not.exported.or.used: p1.C1",
                "- compiler.err.warnings.and.werror",
                "1 error",
                "1 warning");
        if (!output.containsAll(expected)) {
            throw new Exception("Expected output not found");
        }
    }

    @Test
    public void testImplementationMustBeInSameModuleAsProvidesDirective(Path base) throws Exception {
        Path src = base.resolve("src");
        tb.writeJavaFiles(src.resolve("m1x"),
                "module m1x { exports p1; }",
                "package p1; public class C1 { }");
        tb.writeJavaFiles(src.resolve("m2x"),
                "module m2x { requires m1x; requires m3x; provides p1.C1 with p2.C2; }");
        tb.writeJavaFiles(src.resolve("m3x"),
                "module m3x { requires m1x; exports p2; }",
                "package p2; public class C2 extends p1.C1 { }");
        Path modules = base.resolve("modules");
        Files.createDirectories(modules);

        List<String> output = new JavacTask(tb)
                .options("-XDrawDiagnostics", "--module-source-path", src.toString())
                .outdir(modules)
                .files(findJavaFiles(src))
                .run(Task.Expect.FAIL)
                .writeAll()
                .getOutputLines(Task.OutputKind.DIRECT);
        List<String> expected = Arrays.asList(
                "module-info.java:1:42: compiler.err.service.implementation.not.in.right.module: m3x",
                "1 error");
        if (!output.containsAll(expected)) {
            throw new Exception("Expected output not found");
        }
    }
}
