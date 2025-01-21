/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8174073
 * @summary NPE caused by link reference to class
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 * @library /tools/lib
 * @build toolbox.JavacTask toolbox.TestRunner toolbox.ToolBox
 * @run main NPEDuplicateClassNamesTest
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import toolbox.JavacTask;
import toolbox.Task;
import toolbox.TestRunner;
import toolbox.ToolBox;

public class NPEDuplicateClassNamesTest extends TestRunner {

    public static void main(String... args) throws Exception {
        NPEDuplicateClassNamesTest t = new NPEDuplicateClassNamesTest();
        t.runTests();
    }

    private final ToolBox tb = new ToolBox();
    private final String class1 =
            "package com;\n" +
            "/***/\n" +
            "public class MyClass {}";
    private final String class2 =
            "package com;\n" +
            "/**\n" +
            " * The following link tag causes a NullPointerException: {@link Requirements}. \n" +
            " */\n" +
            "public class MyClass {}";

    NPEDuplicateClassNamesTest() throws IOException {
        super(System.err);
    }

    @Test
    public void testDuplicateClassNames() throws IOException {
        Path src = Paths.get("src");
        Path one = src.resolve("one");
        Path two = src.resolve("two");
        Path classes = Paths.get("classes");
        Files.createDirectories(classes);
        tb.writeJavaFiles(one, class1);
        tb.writeJavaFiles(two, class2);

        List<String> expected = Arrays.asList(
                "MyClass.java:5:8: compiler.err.duplicate.class: com.MyClass",
                "MyClass.java:3:65: compiler.err.proc.messager: reference not found",
                "2 errors");
        List<String> output = new JavacTask(tb)
                  .outdir(classes)
                  .options("-XDrawDiagnostics", "-Xdoclint:all,-missing", "-XDdev")
                  .files(tb.findJavaFiles(src))
                  .run(Task.Expect.FAIL)
                  .writeAll()
                  .getOutputLines(Task.OutputKind.DIRECT);

        if (!Objects.equals(output, expected)) {
            throw new IllegalStateException("incorrect output; actual=" + output + "; expected=" + expected);
        }
    }
}
