/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8038455
 * @summary Ensure that formatting diagnostics with an already closed JavaCompiler won't crash
 *          the compiler.
 * @library /tools/lib /tools/javac/lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 *          jdk.jdeps/com.sun.tools.javap
 * @build toolbox.ToolBox JavacTestingAbstractProcessor
 * @run main CompleteOnClosed
 */

import java.util.*;
import javax.annotation.processing.*;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

import toolbox.ToolBox;

public class CompleteOnClosed extends JavacTestingAbstractProcessor {
    public static void main(String... args) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> collector = new DiagnosticCollector<>();
        String source = "class Test extends CompleteOnClosedOther {" +
                        "     class Inner extends Undefined { }" +
                        "}";
        Iterable<JavaFileObject> files = Arrays.<JavaFileObject>asList(new ToolBox.JavaSource(source));
        Iterable<String> options = Arrays.asList("-processor", "CompleteOnClosed");
        CompilationTask task = compiler.getTask(null, null, collector, options, null, files);
        task.call();
        for (Diagnostic<? extends JavaFileObject> d : collector.getDiagnostics()) {
            System.out.println(d.toString());
        }
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return false;
    }
}
