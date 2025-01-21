/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * @test
 * @bug 8168312
 * @summary javac throws NPE if annotation processor is specified and module is declared in a file named arbitrarily
 * @library /tools/lib
 * @modules
 *      jdk.compiler/com.sun.tools.javac.api
 *      jdk.compiler/com.sun.tools.javac.main
 * @build toolbox.ToolBox toolbox.JavacTask ModuleTestBase
 * @run main AnnotationProcessingWithModuleInfoInWrongPlace
 */

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

import toolbox.JavacTask;
import toolbox.Task;

public class AnnotationProcessingWithModuleInfoInWrongPlace extends ModuleTestBase {

    public static void main(String... args) throws Exception {
        new AnnotationProcessingWithModuleInfoInWrongPlace().runTests();
    }

    @Test
    public void testModuleInfoInWrongPlace(Path base) throws Exception {
        Path moduleSrc = base.resolve("module-src");
        Path m = moduleSrc.resolve("m");

        Path classes = base.resolve("classes");

        Files.createDirectories(classes);

        tb.writeJavaFiles(m, "module m {}");

        Path mi = m.resolve("module-info.java");
        Path f = m.resolve("F.java");

        tb.moveFile(mi, f);

        String log = new JavacTask(tb)
                .options("-XDrawDiagnostics",
                         "--module-source-path", moduleSrc.toString(),
                         "-processor", AP.class.getName())
                .outdir(classes)
                .files(findJavaFiles(moduleSrc))
                .run(Task.Expect.FAIL)
                .writeAll()
                .getOutput(Task.OutputKind.DIRECT);


        if (!log.contains("F.java:1:1: compiler.err.module.decl.sb.in.module-info.java"))
            throw new AssertionError("Unexpected output: " + log);
    }

    @SupportedAnnotationTypes("*")
    public static final class AP extends AbstractProcessor {

        @Override
        public boolean process(Set<? extends TypeElement> annot, RoundEnvironment env) {
            return false;
        }

        @Override
        public SourceVersion getSupportedSourceVersion() {
            return SourceVersion.latest();
        }
    }
}
