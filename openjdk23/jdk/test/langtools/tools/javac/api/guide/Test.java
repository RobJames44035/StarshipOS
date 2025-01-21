/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug     6427274 6347778 6469079
 * @summary Various bugs fixed while writing Compiler API Guide
 * @author  Peter von der Ah\u0081
 * @library ../lib
 * @modules java.compiler
 *          jdk.compiler
 * @build ToolTester
 * @compile Test.java
 * @run main Test
 */

import javax.tools.*;
import java.io.File;
import java.io.Reader;
import java.util.Collections;

public class Test extends ToolTester {

    public boolean success = false;

    class DiagnosticTester implements DiagnosticListener<JavaFileObject> {
        public void report(Diagnostic<? extends JavaFileObject> diagnostic) {
            if (diagnostic.getKind() == Diagnostic.Kind.NOTE) {
                // 6427274: FileObject.openReader throws exception
                // 6347778: getSource() returns null for notes
                try (Reader reader = diagnostic.getSource().openReader(true)) {
                    reader.getClass();
                } catch (Exception ex) {
                    throw new AssertionError(ex);
                }
                success = true;
            }
        }
    }

    void test(String... args) throws Exception {
        final Iterable<? extends JavaFileObject> compilationUnits =
            fm.getJavaFileObjects(new File(test_src, "TestMe.java"));
        task = tool.getTask(null, fm, new DiagnosticTester(), null, null, compilationUnits);
        if (!task.call())
            throw new AssertionError("Compilation failed");
        if (!success)
            throw new AssertionError("Did not see a NOTE");
        // 6427274: openReader throws exception
        try (Reader reader = fm.getFileForInput(StandardLocation.PLATFORM_CLASS_PATH,
                           "java.lang",
                           "Object.class").openReader(true)) {
            reader.getClass();
        }
        DiagnosticCollector<JavaFileObject> diags = new DiagnosticCollector<JavaFileObject>();
        task = tool.getTask(null, fm, diags, Collections.singleton("-Xlint:all"),
                            null, compilationUnits);
        if (!task.call())
            throw new AssertionError("Compilation failed");
        String msg = diags.getDiagnostics().get(0).getMessage(null);
        long lineno = diags.getDiagnostics().get(0).getLineNumber();
        if (msg.contains(":"+lineno+":"))
            // 6469079: Diagnostic.getMessage(Locale) includes line numbers
            throw new AssertionError(msg);
    }

    public static void main(String... args) throws Exception {
        try (Test t = new Test()) {
            t.test(args);
        }
    }

}
