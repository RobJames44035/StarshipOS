/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @bug     6733837
 * @summary Compiler API ignores locale settings
 * @author  Maurizio Cimadamore
 * @library ../lib
 * @modules jdk.compiler
 * @build ToolTester
 * @run main T6733837
 */

import java.io.IOException;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import com.sun.source.util.JavacTask;

public class T6733837 extends ToolTester {

    public static void main(String... args) throws IOException {
        try (T6733837 t = new T6733837()) {
            t.exec();
        }
    }

    public void exec() {
        JavaFileObject sfo =
            SimpleJavaFileObject.forSource(URI.create("myfo:/Test.java"),
                                           "\tclass ErroneousWithTab");
        StringWriter sw = new StringWriter();
        PrintWriter out = new PrintWriter(sw);
        List<? extends JavaFileObject> files = Arrays.asList(sfo);
        task = tool.getTask(sw, fm, null, null, null, files);
        try {
            ((JavacTask)task).analyze();
        }
        catch (Throwable t) {
            throw new Error("Compiler threw an exception");
        }
        System.err.println(sw.toString());
        if (!sw.toString().contains("/Test.java"))
            throw new Error("Bad source name in diagnostic");
    }
}
