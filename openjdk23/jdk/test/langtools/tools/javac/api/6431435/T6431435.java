/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6431435 6439406
 * @summary Tree API: source files loaded implicitly from source path
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.file
 * @run main T6431435
 */

import java.io.*;
import java.util.*;
import javax.tools.*;
import com.sun.source.util.*;
import com.sun.tools.javac.api.*;

public class T6431435 {
    public static void main(String... args) throws IOException {
        String testSrc = System.getProperty("test.src", ".");
        String testClasses = System.getProperty("test.classes", ".");
        JavacTool tool = JavacTool.create();
        try (StandardJavaFileManager fm = tool.getStandardFileManager(null, null, null)) {
            fm.setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(new File(".")));
            fm.setLocation(StandardLocation.SOURCE_PATH, Arrays.asList(new File(testSrc)));
            Iterable<? extends JavaFileObject> files = fm.getJavaFileObjectsFromFiles(Arrays.asList(
                    new File(testSrc, "A.java")));
            JavacTask task = tool.getTask(null, fm, null, null, null, files);
            boolean ok = true;
            ok &= check("parse", task.parse(), 1);       // A.java
            ok &= check("analyze", task.analyze(), 3);   // A, Foo, p.B
            ok &= check("generate", task.generate(), 5); // A, Foo, Foo$Baz, Foo$1, p.B
            if (!ok)
                throw new AssertionError("Test failed");
        }
    }

    private static boolean check(String name, Iterable<?> iter, int expect) {
        int found = 0;
        for (Object o: iter) {
            found++;
            //System.err.println(name + " " + found + " " + o);
        }
        if (found == expect)
            return true;
        System.err.println(name + ": found " + found + " -- expected " + expect);
        return false;
    }
}
