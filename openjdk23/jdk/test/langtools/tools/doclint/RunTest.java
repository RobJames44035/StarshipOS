/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/* @test
 * @bug 8004832 8000103
 * @summary Add new doclint package
 * @summary Create doclint utility
 * @modules jdk.javadoc/jdk.javadoc.internal.doclint
 */

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import jdk.javadoc.internal.doclint.DocLint;
import jdk.javadoc.internal.doclint.DocLint.BadArgs;

/** javadoc error on toplevel:  a & b. */
public class RunTest {
    /** javadoc error on member: a < b */
    public static void main(String... args) throws Exception {
        new RunTest().run();
    }


    File testSrc = new File(System.getProperty("test.src"));
    File thisFile = new File(testSrc, RunTest.class.getSimpleName() + ".java");

    void run() throws Exception {
        for (Method m: getClass().getDeclaredMethods()) {
            Annotation a = m.getAnnotation(Test.class);
            if (a != null) {
                System.err.println("test: " + m.getName());
                try {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    m.invoke(this, new Object[] { pw });
                    String out = sw.toString();
                    System.err.println(">>> " + out.replace("\n", "\n>>> "));
                    if (!out.contains("a < b"))
                        error("\"a < b\" not found");
                    if (!out.contains("a & b"))
                        error("\"a & b\" not found");
                } catch (InvocationTargetException e) {
                    Throwable cause = e.getCause();
                    throw (cause instanceof Exception) ? ((Exception) cause) : e;
                }
                System.err.println();
            }
        }

        if (errors > 0)
            throw new Exception(errors + " errors occurred");
    }


    void error(String msg) {
        System.err.println("Error: " + msg);
        errors++;
    }

    int errors;

    /** Marker annotation for test cases. */
    @Retention(RetentionPolicy.RUNTIME)
    @interface Test { }

    @Test
    void testMain(PrintWriter pw) throws BadArgs, IOException {
        String[] args = { "-Xmsgs", thisFile.getPath() };
        DocLint d = new DocLint();
        d.run(pw, args);
    }
}


