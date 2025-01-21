/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @bug 6729471
 * @summary javap does not output inner interfaces of an interface
 * @library /tools/lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 *          jdk.jdeps/com.sun.tools.javap
 * @build toolbox.ToolBox toolbox.JarTask
 * @run main T6729471
 */

import java.io.*;
import java.net.*;
import java.util.*;
import javax.tools.*;

import toolbox.JarTask;
import toolbox.ToolBox;

public class T6729471
{
    public static void main(String... args) throws IOException {
        new T6729471().run();
    }

    void run() throws IOException {
        File testClasses = new File(System.getProperty("test.classes"));

        // simple class
        verify("java.util.Map",
                "public abstract boolean containsKey(java.lang.Object)");

        // inner class
        verify("java.util.Map.Entry",
                "public abstract K getKey()");

        // file name
        verify(new File(testClasses, "T6729471.class").getPath(),
                "public static void main(java.lang.String...)");

        // file url
        verify(new File(testClasses, "T6729471.class").toURI().toString(),
                "public static void main(java.lang.String...)");

        // jar url
        File testJar = createJar("test.jar", "java.util.*");
        try {
            verify("jar:" + testJar.toURL() + "!/java/util/Map.class",
                "public abstract boolean containsKey(java.lang.Object)");
        } catch (MalformedURLException e) {
            error(e.toString());
        }

        if (errors > 0)
            throw new Error(errors + " found.");
    }

    File createJar(String name, String... paths) throws IOException {
        JavaCompiler comp = ToolProvider.getSystemJavaCompiler();
        try (JavaFileManager fm = comp.getStandardFileManager(null, null, null)) {
            File f = new File(name);
            ToolBox tb = new ToolBox();
            new JarTask(tb, f.getPath())
                .files(fm, StandardLocation.PLATFORM_CLASS_PATH, paths)
                .run();
            return f;
        }
    }

    void verify(String className, String... expects) {
        String output = javap(className);
        for (String expect: expects) {
            if (output.indexOf(expect)< 0)
                error(expect + " not found");
        }
    }

    void error(String msg) {
        System.err.println(msg);
        errors++;
    }

    int errors;

    String javap(String className) {
        String testClasses = System.getProperty("test.classes", ".");
        StringWriter sw = new StringWriter();
        PrintWriter out = new PrintWriter(sw);
        String[] args = { "-classpath", testClasses, className };
        int rc = com.sun.tools.javap.Main.run(args, out);
        out.close();
        String output = sw.toString();
        System.out.println("class " + className);
        System.out.println(output);
        if (rc != 0)
            throw new Error("javap failed. rc=" + rc);
        if (output.indexOf("Error:") != -1)
            throw new Error("javap reported error.");
        return output;
    }
}

