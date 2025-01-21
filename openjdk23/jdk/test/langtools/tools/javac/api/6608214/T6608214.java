/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @bug     6608214
 * @summary Exception throw while analysing a file with error
 * @author  Maurizio Cimadamore
 * @modules jdk.compiler
 */

import com.sun.source.util.JavacTask;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;

public class T6608214 {
    public static void main(String[] args) throws IOException {
        JavaFileObject sfo =
            SimpleJavaFileObject.forSource(URI.create(""),
                                           "class Test<S> { <T extends S & Runnable> void test(){}}");
        List<? extends JavaFileObject> files = Arrays.asList(sfo);
        List<String> opts = Arrays.asList("-Xjcov");
        JavaCompiler tool = ToolProvider.getSystemJavaCompiler();
        JavacTask ct = (JavacTask)tool.getTask(null, null, null,opts,null,files);
        ct.analyze();
    }
}
