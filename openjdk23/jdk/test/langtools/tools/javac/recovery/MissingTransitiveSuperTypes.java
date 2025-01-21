/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/**
 * @test
 * @bug 8284283
 * @summary Verify javac's error recovery can handle multiple missing transitive supertypes
 * @library /tools/lib
 * @modules jdk.compiler/com.sun.tools.javac.main
 *          jdk.compiler/com.sun.tools.javac.api
 * @build toolbox.ToolBox toolbox.JavacTask
 * @run main MissingTransitiveSuperTypes
 */

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Arrays;

import toolbox.ToolBox;
import toolbox.TestRunner;
import toolbox.JavacTask;
import toolbox.Task;

public class MissingTransitiveSuperTypes extends TestRunner {
    ToolBox tb;

    public MissingTransitiveSuperTypes() {
        super(System.err);
        tb = new ToolBox();
    }

    public static void main(String[] args) throws Exception {
        new MissingTransitiveSuperTypes().runTests(m -> new Object[] {Paths.get(m.getName())});
    }

    @Test
    public void testMultipleTransitiveSuperTypesMissing(Path base) throws Exception {
        Path libClasses = base.resolve("libclasses");
        Files.createDirectories(libClasses);
        new JavacTask(tb)
            .outdir(libClasses)
            .sources("""
                     package lib;
                     public class Lib implements A, B {}
                     """,
                     """
                     package lib;
                     public interface A {}
                     """,
                     """
                     package lib;
                     public interface B {}
                     """)
            .options()
            .run()
            .writeAll();
        Files.delete(libClasses.resolve("lib").resolve("A.class"));
        Files.delete(libClasses.resolve("lib").resolve("B.class"));
        String code = """
                      public class Test<E> extends lib.Lib {}
                      """;
        List<String> output = new JavacTask(tb)
                .classpath(libClasses)
                .sources(code)
                .options("-XDrawDiagnostics", "-XDdev")
                .run(Task.Expect.FAIL)
                .writeAll()
                .getOutputLines(Task.OutputKind.DIRECT);
        List<String> expected = Arrays.asList(
                "Test.java:1:33: compiler.err.cant.access: lib.A, (compiler.misc.class.file.not.found: lib.A)",
                "Test.java:1:8: compiler.err.cant.access: lib.B, (compiler.misc.class.file.not.found: lib.B)",
                "2 errors");
        tb.checkEqual(expected, output);
    }

}
