/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6358168
 * @summary JavaCompiler.hasBeenUsed is not set in delegateCompiler
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.file
 *          jdk.compiler/com.sun.tools.javac.main
 *          jdk.compiler/com.sun.tools.javac.util
 */

import java.io.*;
import java.util.*;

import javax.annotation.processing.*;
import javax.lang.model.element.*;
import javax.tools.*;

import com.sun.source.util.JavacTask;
import com.sun.tools.javac.api.JavacTool;
import com.sun.tools.javac.file.JavacFileManager;
import com.sun.tools.javac.main.JavaCompiler;
import com.sun.tools.javac.util.*;
import com.sun.tools.javac.util.List; // disambiguate


@SupportedAnnotationTypes("*")
public class T6358168 extends AbstractProcessor {
    private static final String testClasses = System.getProperty("test.classes");
    private static final String testSrc = System.getProperty("test.src");
    private static final String self = T6358168.class.getName();

    public static void main(String... args) throws Throwable {

        JavacFileManager fm = new JavacFileManager(new Context(), false, null);
        List<JavaFileObject> files = toList(fm.getJavaFileObjects(new File(testSrc, self + ".java")));

        try {
            // first, test case with no annotation processing
            testNoAnnotationProcessing(fm, files);

            // now, test case with annotation processing
            testAnnotationProcessing(fm, files);
        }
        catch (Throwable t) {
            throw new AssertionError(t);
        }
    }

    static void testNoAnnotationProcessing(JavacFileManager fm, List<JavaFileObject> files) throws Throwable {
        Context context = new Context();

        String[] args = { "-d", "." };

        JavacTool tool = JavacTool.create();
        JavacTask task = tool.getTask(null, fm, null, List.from(args), null, files, context);
        // no need in this simple case to call task.prepareCompiler(false)

        JavaCompiler compiler = JavaCompiler.instance(context);
        compiler.compile(files);
        try {
            compiler.compile(files);
            throw new Error("Error: AssertionError not thrown after second call of compile");
        } catch (AssertionError e) {
            System.err.println("Exception from compiler (expected): " + e);
        }
    }

    static void testAnnotationProcessing(JavacFileManager fm, List<JavaFileObject> files) throws Throwable {
        Context context = new Context();

        String[] args = {
                "-XprintRounds",
                "-processorpath", testClasses,
                "-processor", self,
                "-d", "."
        };

        JavacTool tool = JavacTool.create();
        JavacTask task = tool.getTask(null, fm, null, List.from(args), null, files, context);
        // no need in this simple case to call task.prepareCompiler(false)

        JavaCompiler compiler = JavaCompiler.instance(context);
        compiler.compile(files);
        try {
            compiler.compile(files);
            throw new Error("Error: AssertionError not thrown after second call of compile");
        } catch (AssertionError e) {
            System.err.println("Exception from compiler (expected): " + e);
        }
    }

    private static List<JavaFileObject> toList(Iterable<? extends JavaFileObject> iter) {
        ListBuffer<JavaFileObject> files = new ListBuffer<>();
        for (JavaFileObject file: iter)
            files.add(file);
        return files.toList();
    }

    @Override
    public boolean process(Set<? extends TypeElement> tes, RoundEnvironment renv) {
        return true;
    }
}
