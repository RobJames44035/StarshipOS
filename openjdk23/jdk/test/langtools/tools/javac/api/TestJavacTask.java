/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug     4813736 8015073
 * @summary Provide a basic test of access to the Java Model from javac, and error messages
 * @author  Peter von der Ah\u00e9
 * @modules jdk.compiler/com.sun.tools.javac.api
 * @run main TestJavacTask TestJavacTask.java
 */

import com.sun.tools.javac.api.JavacTaskImpl;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.lang.model.element.Element;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class TestJavacTask {
    static final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    static final StandardJavaFileManager fm = compiler.getStandardFileManager(null, null, null);
    static JavacTaskImpl getTask(File... file) {
        Iterable<? extends JavaFileObject> files =
            fm.getJavaFileObjectsFromFiles(Arrays.asList(file));
        return (JavacTaskImpl)compiler.getTask(null, fm, null, null, null, files);
    }

    static void basicTest(String... args) throws IOException {
        String srcdir = System.getProperty("test.src");
        File file = new File(srcdir, args[0]);
        JavacTaskImpl task = getTask(file);
        for (Element clazz : task.enter(task.parse()))
            System.out.println(clazz.getSimpleName());
    }

    static void checkKindError() {
        final File testFile = new File("Test.java "); // <-note trailing space!
        try {
            getTask(testFile);
        } catch (IllegalArgumentException iae) {
            // The following check is somewhat fragile, since the content of the ILA is not
            // formally specified. If we want to fix this, we should catch/rewrap ILA coming
            // from use of java.nio.file.Path inside javac's impl of JavaFileManager.
            if (!iae.getMessage().contains(testFile.getName())) {
                System.err.println("Got message: " + iae.getMessage());
                throw new RuntimeException("Error: expected string not found");
            }
        }
    }

    public static void main(String... args) throws IOException {
        try {
            basicTest(args);
            checkKindError();
        } finally {
            fm.close();
        }
    }
}
