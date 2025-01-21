/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6414633 6440109
 * @summary Only the first processor message at a source location is reported
 * @library /tools/javac/lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.file
 * @build    JavacTestingAbstractProcessor A T6414633
 * @run main T6414633
 */

import java.io.*;
import java.util.*;
import javax.annotation.processing.*;
import javax.lang.model.*;
import javax.lang.model.element.*;
import javax.tools.*;
import com.sun.source.util.*;
import com.sun.tools.javac.api.*;

public class T6414633 {
    public static void main(String... args) throws IOException {
        String testSrc = System.getProperty("test.src", ".");
        String testClasses = System.getProperty("test.classes", ".");
        String testClassPath = System.getProperty("test.class.path", testClasses);

        JavacTool tool = JavacTool.create();
        MyDiagListener dl = new MyDiagListener();
        try (StandardJavaFileManager fm = tool.getStandardFileManager(dl, null, null)) {
            try {
                fm.setLocation(StandardLocation.CLASS_PATH, pathToFiles(testClassPath));
            } catch (IOException e) {
                throw new AssertionError(e);
            }
            Iterable<? extends JavaFileObject> files =
                fm.getJavaFileObjectsFromFiles(Arrays.asList(new File(testSrc, A.class.getName()+".java")));
            String[] opts = { "-proc:only",
                              "-processor", A.class.getName() };
            JavacTask task = tool.getTask(null, fm, dl, Arrays.asList(opts), null, files);
            task.call();

            // two annotations on the same element -- expect 2 diags from the processor
            if (dl.diags != 2)
                throw new AssertionError(dl.diags + " diagnostics reported");
        }
    }

    private static List<File> pathToFiles(String path) {
        List<File> list = new ArrayList<File>();
        for (String s: path.split(File.pathSeparator)) {
            if (!s.isEmpty())
                list.add(new File(s));
        }
        return list;
    }

    private static class MyDiagListener implements DiagnosticListener<JavaFileObject>
    {
        public void report(Diagnostic d) {
            System.err.println(d);
            diags++;
        }

        int diags;
    }
}
