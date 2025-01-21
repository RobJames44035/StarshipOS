/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/**
 * @test
 * @bug 8277105
 * @summary Verify missing permitted subtype is handled properly for both casts and pattern switches.
 * @library /tools/lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 *          jdk.compiler/com.sun.tools.javac.util
 * @build toolbox.ToolBox toolbox.JavacTask
 * @run main MissingPermittedSubtypes
*/

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import toolbox.TestRunner;
import toolbox.JavacTask;
import toolbox.Task;
import toolbox.ToolBox;

public class MissingPermittedSubtypes extends TestRunner {

    private static final String JAVA_VERSION = System.getProperty("java.specification.version");

    ToolBox tb;

    public static void main(String... args) throws Exception {
        new MissingPermittedSubtypes().runTests();
    }

    MissingPermittedSubtypes() {
        super(System.err);
        tb = new ToolBox();
    }

    public void runTests() throws Exception {
        runTests(m -> new Object[] { Paths.get(m.getName()) });
    }

    @Test
    public void testInaccessiblePermitted(Path base) throws IOException {
        Path current = base.resolve(".");
        Path libSrc = current.resolve("lib-src");

        tb.writeJavaFiles(libSrc,
                           """
                           package lib;
                           public sealed interface S permits A, B1, B2 {}
                           """,
                           """
                           package lib;
                           public final class A implements S {}
                           """,
                           """
                           package lib;
                           final class B1 implements S {}
                           """,
                           """
                           package lib;
                           final class B2 implements S, Runnable {
                               public void run() {}
                           }
                           """);

        Path libClasses = current.resolve("libClasses");

        Files.createDirectories(libClasses);

        new JavacTask(tb)
                .outdir(libClasses)
                .files(tb.findJavaFiles(libSrc))
                .run();

        Path b1Class = libClasses.resolve("lib").resolve("B1.class");

        Files.delete(b1Class);

        Path b2Class = libClasses.resolve("lib").resolve("B2.class");

        Files.delete(b2Class);

        {
            Path src1 = current.resolve("src1");
            tb.writeJavaFiles(src1,
                               """
                               package test;
                               import lib.*;
                               public class Test1 {
                                   private void test(S obj) {
                                       int i = switch (obj) {
                                           case A a -> 0;
                                       };
                                       Runnable r = () -> {obj = null;};
                                   }
                               }
                               """);

            Path classes1 = current.resolve("classes1");

            Files.createDirectories(classes1);

            var log =
                    new JavacTask(tb)
                        .options("-XDrawDiagnostics",
                                 "--class-path", libClasses.toString())
                        .outdir(classes1)
                        .files(tb.findJavaFiles(src1))
                        .run(Task.Expect.FAIL)
                        .writeAll()
                        .getOutputLines(Task.OutputKind.DIRECT);

            List<String> expectedErrors = List.of(
                   "Test1.java:5:24: compiler.err.cant.access: lib.B1, (compiler.misc.class.file.not.found: lib.B1)",
                   "Test1.java:8:29: compiler.err.cant.ref.non.effectively.final.var: obj, (compiler.misc.lambda)",
                   "2 errors");

            if (!expectedErrors.equals(log)) {
                throw new AssertionError("Incorrect errors, expected: " + expectedErrors +
                                          ", actual: " + log);
            }
        }

        {
            Path src2 = current.resolve("src2");
            tb.writeJavaFiles(src2,
                               """
                               package test;
                               import lib.*;
                               public class Test1 {
                                   private void test(S obj) {
                                    Runnable r = (Runnable) obj;
                                    String s = (String) obj;
                                   }
                               }
                               """);

            Path classes2 = current.resolve("classes2");

            Files.createDirectories(classes2);

            var log =
                    new JavacTask(tb)
                        .options("-XDrawDiagnostics",
                                 "--class-path", libClasses.toString())
                        .outdir(classes2)
                        .files(tb.findJavaFiles(src2))
                        .run(Task.Expect.FAIL)
                        .writeAll()
                        .getOutputLines(Task.OutputKind.DIRECT);

            List<String> expectedErrors = List.of(
                   "Test1.java:5:19: compiler.err.cant.access: lib.B1, (compiler.misc.class.file.not.found: lib.B1)",
                   "Test1.java:6:26: compiler.err.prob.found.req: (compiler.misc.inconvertible.types: lib.S, java.lang.String)",
                   "2 errors");

            if (!expectedErrors.equals(log)) {
                throw new AssertionError("Incorrect errors, expected: " + expectedErrors +
                                          ", actual: " + log);
            }
        }
    }

}
