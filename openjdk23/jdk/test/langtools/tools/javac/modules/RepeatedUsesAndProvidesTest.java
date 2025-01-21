/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/**
 * @test 8145012
 * @summary Javac doesn't report errors on duplicate uses or provides
 * @library /tools/lib
 * @modules
 *      jdk.compiler/com.sun.tools.javac.api
 *      jdk.compiler/com.sun.tools.javac.main
 * @build toolbox.ToolBox toolbox.JavacTask ModuleTestBase
 * @run main RepeatedUsesAndProvidesTest
 */

import java.nio.file.Files;
import java.nio.file.Path;

import toolbox.JavacTask;
import toolbox.Task;
import toolbox.ToolBox;

public class RepeatedUsesAndProvidesTest extends ModuleTestBase {
    public static void main(String... args) throws Exception {
        RepeatedUsesAndProvidesTest t = new RepeatedUsesAndProvidesTest();
        t.runTests();
    }

    @Test
    public void testDuplicateUses(Path base) throws Exception {
        Path src = base.resolve("src");
        tb.writeJavaFiles(src,
                "module m { uses p1.C1; uses p1.C1; }",
                "package p1; public class C1 {}");
        Path classes = base.resolve("classes");
        Files.createDirectories(classes);

        String log = new JavacTask(tb)
                .options("-XDrawDiagnostics")
                .outdir(classes)
                .files(findJavaFiles(src))
                .run(Task.Expect.FAIL)
                .writeAll()
                .getOutput(Task.OutputKind.DIRECT);
        if (!log.contains("module-info.java:1:24: compiler.err.duplicate.uses: p1.C1"))
            throw new Exception("expected output not found");
    }

    @Test
    public void testDuplicateProvides(Path base) throws Exception {
        Path src = base.resolve("src");
        tb.writeJavaFiles(src,
                "module m { provides p1.C1 with p2.C2; provides p1.C1 with p2.C2; }",
                "package p1; public class C1 {}",
                "package p2; public class C2 extends p1.C1 {}");
        Path classes = base.resolve("classes");
        Files.createDirectories(classes);

        String log = new JavacTask(tb)
                .options("-XDrawDiagnostics")
                .outdir(classes)
                .files(findJavaFiles(src))
                .run(Task.Expect.FAIL)
                .writeAll()
                .getOutput(Task.OutputKind.DIRECT);
        if (!log.contains("module-info.java:1:61: compiler.err.duplicate.provides"))
            throw new Exception("expected output not found");
    }
}
