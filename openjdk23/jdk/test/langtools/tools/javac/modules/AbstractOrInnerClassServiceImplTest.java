/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/**
 * @test 8145016
 * @summary Javac doesn't report errors on service implementation which cannot be initialized
 * @library /tools/lib
 * @modules
 *      jdk.compiler/com.sun.tools.javac.api
 *      jdk.compiler/com.sun.tools.javac.main
 *      jdk.jdeps/com.sun.tools.javap
 * @build toolbox.ToolBox toolbox.JavacTask ModuleTestBase
 * @run main AbstractOrInnerClassServiceImplTest
 */

import java.nio.file.Files;
import java.nio.file.Path;

import toolbox.JavacTask;
import toolbox.Task;
import toolbox.ToolBox;

public class AbstractOrInnerClassServiceImplTest extends ModuleTestBase {
    public static void main(String... args) throws Exception {
        AbstractOrInnerClassServiceImplTest t = new AbstractOrInnerClassServiceImplTest();
        t.runTests();
    }

    @Test
    public void testAbstractServiceImpl(Path base) throws Exception {
        Path src = base.resolve("src");
        tb.writeJavaFiles(src,
                "module m { provides p1.Service with p2.Impl; }",
                "package p1; public interface Service { }",
                "package p2; public interface Impl extends p1.Service { }");
        Path classes = base.resolve("classes");
        Files.createDirectories(classes);

        String log = new JavacTask(tb)
                .options("-XDrawDiagnostics")
                .outdir(classes)
                .files(findJavaFiles(src))
                .run(Task.Expect.FAIL)
                .writeAll()
                .getOutput(Task.OutputKind.DIRECT);
        if (!log.contains("module-info.java:1:39: compiler.err.service.implementation.is.abstract: p2.Impl"))
            throw new Exception("expected output not found");
    }

    @Test
    public void testInnerClassServiceImpl(Path base) throws Exception {
        Path src = base.resolve("src");
        tb.writeJavaFiles(src,
                "module m { provides p1.Service with p2.Outer.Inner; }",
                "package p1; public interface Service { }",
                "package p2; public class Outer { public class Inner implements p1.Service {} }");
        Path classes = base.resolve("classes");
        Files.createDirectories(classes);

        String log = new JavacTask(tb)
                .options("-XDrawDiagnostics")
                .outdir(classes)
                .files(findJavaFiles(src))
                .run(Task.Expect.FAIL)
                .writeAll()
                .getOutput(Task.OutputKind.DIRECT);
        if (!log.contains("module-info.java:1:45: compiler.err.service.implementation.is.inner: p2.Outer.Inner"))
            throw new Exception("expected output not found");
    }

    @Test
    public void testInnerInterfaceServiceImpl(Path base) throws Exception {
        Path src = base.resolve("src");
        tb.writeJavaFiles(src,
                "module m { provides p1.Service with p2.Outer.Inner; }",
                "package p1; public interface Service { }",
                "package p2; public class Outer { public interface Inner extends p1.Service {} }");
        Path classes = base.resolve("classes");
        Files.createDirectories(classes);

        String log = new JavacTask(tb)
                .options("-XDrawDiagnostics")
                .outdir(classes)
                .files(findJavaFiles(src))
                .run(Task.Expect.FAIL)
                .writeAll()
                .getOutput(Task.OutputKind.DIRECT);
        if (!log.contains("module-info.java:1:45: compiler.err.service.implementation.is.abstract: p2.Outer.Inner"))
            throw new Exception("expected output not found");
    }
}
