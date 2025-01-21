/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * @test
 * @bug 8164590 8170691
 * @summary Test use of ALL-DEFAULT token
 * @library /tools/lib
 * @modules
 *      jdk.compiler/com.sun.tools.javac.api
 *      jdk.compiler/com.sun.tools.javac.main
 * @build toolbox.ToolBox toolbox.JarTask toolbox.JavacTask toolbox.JavaTask ModuleTestBase
 * @run main AllDefaultTest
 */

import java.nio.file.Path;

import toolbox.JavacTask;
import toolbox.Task;

public class AllDefaultTest extends ModuleTestBase {
    public static void main(String... args) throws Exception {
        AllDefaultTest t = new AllDefaultTest();
        t.runTests();
    }

    @Test
    public void testCompileTime_notAllowed(Path base) throws Exception {
        tb.writeJavaFiles(base, "class C { }");
        String out = new JavacTask(tb)
                .options("-XDrawDiagnostics",
                        "--add-modules=ALL-DEFAULT")
                .files(tb.findJavaFiles(base))
                .run(Task.Expect.FAIL)
                .writeAll()
                .getOutput(Task.OutputKind.DIRECT);

        if (!out.contains("- compiler.err.bad.name.for.option: --add-modules, ALL-DEFAULT")) {
            error("expected text not found");
        }
    }

    @Test
    public void testRuntimeTime_ignored_1(Path base) throws Exception {
        tb.writeJavaFiles(base, "class C { }");
        new JavacTask(tb, Task.Mode.EXEC)
                .options("-XDrawDiagnostics",
                        "-J--add-modules=ALL-DEFAULT",
                        "--inherit-runtime-environment")
                .files(tb.findJavaFiles(base))
                .run()
                .writeAll();
    }

    @Test
    public void testRuntimeTime_ignored_2(Path base) throws Exception {
        tb.writeJavaFiles(base, "class C { }");
        new JavacTask(tb, Task.Mode.EXEC)
                .options("-XDrawDiagnostics",
                        "-J--add-modules=jdk.compiler",
                        "--inherit-runtime-environment")
                .files(tb.findJavaFiles(base))
                .run()
                .writeAll();
    }
}
