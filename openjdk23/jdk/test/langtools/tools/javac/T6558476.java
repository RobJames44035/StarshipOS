/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @bug 6558476 5071352
 * @summary com/sun/tools/javac/Main.compile don't release file handles on return
 * @library /tools/lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 *          jdk.jdeps/com.sun.tools.javap
 * @build toolbox.ToolBox toolbox.JarTask toolbox.JavacTask
 * @run main/othervm -Xmx512m -Xms512m  T6558476
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import com.sun.tools.javac.Main;

import toolbox.JarTask;
import toolbox.JavacTask;
import toolbox.ToolBox;

public class T6558476 {

    private static final String classFoo = "class Foo {}";
    private static final String classMyFoo =
        "class MyFoo {\n" +
         "  public static void main(String[] args) {\n"+
         "    new Foo();\n"+
         "  }\n"+
        "}";

    public static void main(String[] args) throws IOException {
        ToolBox tb = new ToolBox();

        new JavacTask(tb)
            .sources(classFoo)
            .run();
        new JarTask(tb, "foo.jar")
            .files("Foo.class")
            .run();

        new JavacTask(tb)
            .classpath("foo.jar")
            .sources(classMyFoo)
            .run();
        File foo_jar = new File("foo.jar");
        if (foo_jar.delete()) {
            System.out.println("jar file successfully deleted");
        } else {
            throw new Error("Error deleting file \"" + foo_jar.getPath() + "\"");
        }
    }
}
