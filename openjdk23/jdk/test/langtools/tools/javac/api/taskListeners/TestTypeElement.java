/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8283661 8283714
 * @summary Unexpected TypeElement in ANALYZE TaskEvent
 * @modules jdk.compiler
 * @run main TestTypeElement
 */

import java.io.PrintStream;
import java.net.URI;
import java.util.List;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;

import com.sun.source.util.JavacTask;
import com.sun.source.util.TaskEvent;
import com.sun.source.util.TaskListener;

public class TestTypeElement {
    public static void main(String... args) throws Exception {
        new TestTypeElement().run();
    }

    private PrintStream log;
    private Elements elements;
    private int errors;

    void run() throws Exception {
        log = System.err;

        List<JavaFileObject> files = List.of(
                createFileObject("module-info.java", "module m { }"),
                createFileObject("p/package-info.java", "/** Comment. */ package p;"),
                createFileObject("p/C.java", "package p; public class C { }")
        );

        JavaCompiler c = ToolProvider.getSystemJavaCompiler();
        JavacTask t = (JavacTask) c.getTask(null, null, null,  List.of("-d", "classes"), null, files);
        t.addTaskListener(new TaskListener() {
            @Override
            public void started(TaskEvent e) {
                log.println("started: " + e);
                checkTypeElement(e);
            }
            @Override
            public void finished(TaskEvent e) {
                log.println("finished: " + e);
                checkTypeElement(e);
            }
        });
        elements = t.getElements();
        t.call();

        if (errors > 0) {
            log.println(errors + " errors occurred");
            throw new Exception(errors + " errors occurred");
        }
    }

    private void checkTypeElement(TaskEvent e) {
        TypeElement te = e.getTypeElement();

        if (te != null) {
            showTypeElement(e.getTypeElement());
        }

        switch (e.getKind()) {
            case COMPILATION, PARSE, ENTER -> {
                checkEqual(te, null);
            }

            case ANALYZE, GENERATE -> {
                if (te == null) {
                    error("type element is null");
                    return;
                }


                switch (te.getQualifiedName().toString()) {
                    case "m.module-info" -> {
                        checkEqual(elements.getModuleOf(te), elements.getModuleElement("m"));
                        checkEqual(elements.getPackageOf(te), null);
                    }
                    case "p.package-info", "p.C" -> {
                        checkEqual(elements.getModuleOf(te), elements.getModuleElement("m"));
                        checkEqual(elements.getPackageOf(te), elements.getPackageElement("p"));
                    }
                }
            }
        }
    }

    private void showTypeElement(TypeElement e) {
        log.println("type element: " + e);

        try {
            log.println("    module element: " + elements.getModuleOf(e));
        } catch (Throwable t) {
            log.println("    module element: " + t);
        }

        try {
            log.println("    package element: " + elements.getPackageOf(e));
        } catch (Throwable t) {
            log.println("    package element: " + t);
        }
    }

    private <T> void checkEqual(T found, T expected) {
        if (found != expected) {
            error("mismatch");
            log.println("   found: " + found);
            log.println("expected: " + expected);
        }
    }

    private void error(String message) {
        log.println("Error: " + message);
        errors++;
    }

    private JavaFileObject createFileObject(String name, String body) {
        return SimpleJavaFileObject.forSource(URI.create("myfo:///" + name),
                                              body);
    }
}