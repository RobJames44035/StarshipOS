/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8024437
 * @summary Inferring the exception thrown by a lambda: sometimes fails to compile
 * @library /tools/lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 *          jdk.jdeps/com.sun.tools.javap
 * @build toolbox.ToolBox toolbox.JavacTask
 * @run main ExceptionInferenceFromClassFileTest
 */

import java.nio.file.Files;
import java.nio.file.Paths;

import toolbox.JavacTask;
import toolbox.ToolBox;

public class ExceptionInferenceFromClassFileTest {

    static final String ABSrc =
            "class B {\n" +
            "    public static <E extends Throwable> void t(A<E> a) throws E {\n" +
            "        a.run();\n" +
            "    }\n" +
            "}\n" +

            "interface A<E extends Throwable> {\n" +
            "    void run() throws E;\n" +
            "}";

    static final String CSrc =
            "class C {\n" +
            "    public void d() {\n" +
            "        B.t(null);\n" +
            "    }\n" +
            "}";

    public static void main(String[] args) throws Exception {
        ToolBox tb = new ToolBox();
        tb.createDirectories("out");

        new JavacTask(tb)
                .outdir("out")
                .sources(ABSrc)
                .run();

        new JavacTask(tb)
                .outdir("out")
                .classpath("out")
                .sources(CSrc)
                .run();
    }

}
