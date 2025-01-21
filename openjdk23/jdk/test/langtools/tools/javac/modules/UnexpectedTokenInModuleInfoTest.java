/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/**
 * @test
 * @bug 8166420
 * @summary Confusing error message when reading bad module declaration
 * @library /tools/lib
 * @modules
 *      jdk.compiler/com.sun.tools.javac.api
 *      jdk.compiler/com.sun.tools.javac.main
 * @build toolbox.ToolBox toolbox.JavacTask ModuleTestBase
 * @run main UnexpectedTokenInModuleInfoTest
 */

import java.nio.file.*;
import java.util.List;
import java.util.Arrays;

import toolbox.JavacTask;
import toolbox.Task;

public class UnexpectedTokenInModuleInfoTest extends ModuleTestBase {
    public static void main(String... args) throws Exception {
        UnexpectedTokenInModuleInfoTest t = new UnexpectedTokenInModuleInfoTest();
        t.runTests();
    }

    @Test
    public void testSingleModule(Path base) throws Exception {
        Path src = base.resolve("src");
        tb.writeFile(src.resolve("module-info.java"), "weak module m { }");

         List<String> output = new JavacTask(tb)
            .options("-XDrawDiagnostics")
            .files(src.resolve("module-info.java"))
            .run(Task.Expect.FAIL)
            .writeAll()
            .getOutputLines(Task.OutputKind.DIRECT);

         List<String> expected = Arrays.asList("module-info.java:1:1: compiler.err.expected.module.or.open",
                "1 error");
        if (!output.containsAll(expected)) {
            throw new Exception("Expected output not found");
        }
    }
}