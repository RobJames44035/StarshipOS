/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/**
 * @test
 * @bug 8178701
 * @summary Check javac can generate code for protected enums accessible through subclassing.
 * @library /tools/lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 *          jdk.compiler/com.sun.tools.javac.util
 * @build toolbox.ToolBox toolbox.JavacTask
 * @run main EnumAccessible
*/

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import toolbox.TestRunner;
import toolbox.JavacTask;
import toolbox.JavaTask;
import toolbox.Task;
import toolbox.ToolBox;

public class EnumAccessible extends TestRunner {

    ToolBox tb;

    public static void main(String... args) throws Exception {
        new EnumAccessible().runTests();
    }

    EnumAccessible() {
        super(System.err);
        tb = new ToolBox();
    }

    public void runTests() throws Exception {
        runTests(m -> new Object[] { Paths.get(m.getName()) });
    }

    @Test
    public void testPattern(Path base) throws Exception {
        Path current = base.resolve(".");
        Path src = current.resolve("src");
        Path classes = current.resolve("classes");
        tb.writeJavaFiles(src,
                          """
                          package foo;
                          import bar.D.E;
                          public class A {
                              public static class B {
                                  protected enum C {
                                      X, Y, Z
                                  }
                              }
                              public static void main(String... args) {
                                  new E().run(B.C.X);
                              }
                          }
                          """,
                          """
                          package bar;
                          import foo.A.B;
                          public class D {
                              public static class E extends B {
                                  public void run(C arg) {
                                      switch (arg) {
                                          default: System.out.println("OK");
                                      }
                                  }
                              }
                          }
                          """);

        Files.createDirectories(classes);

        new JavacTask(tb)
            .options("-doe")
            .outdir(classes)
            .files(tb.findJavaFiles(src))
            .run(Task.Expect.SUCCESS)
            .writeAll();

        var out = new JavaTask(tb)
                .classpath(classes.toString())
                .className("foo.A")
                .run()
                .writeAll()
                .getOutputLines(Task.OutputKind.STDOUT);

        var expectedOut = List.of("OK");

        if (!Objects.equals(expectedOut, out)) {
            throw new AssertionError("Incorrect Output, expected: " + expectedOut +
                                      ", actual: " + out);

        }
    }

}
