/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug     6265137
 * @summary setOption() and setExtendedOption() of JavacTool will throw exception for some defined options
 * @author  Peter von der Ah\u00e9
 * @modules java.compiler
 *          jdk.compiler
 */

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.tools.*;

public class T6265137 {
    public static void main(String... args) throws IOException {
        JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
        DiagnosticListener<JavaFileObject> dl =  new DiagnosticListener<JavaFileObject>() {
                public void report(Diagnostic<? extends JavaFileObject> message) {
                    System.out.println(message.getSource()
                                       +":"+message.getStartPosition()+":"
                                       +message.getStartPosition()+":"+message.getPosition());
                    System.out.println(message.toString());
                    System.out.format("Found problem: %s%n", message.getCode());
                    System.out.flush();
                }
        };
        try (StandardJavaFileManager fm = javac.getStandardFileManager(dl, null, null)) {
            String srcdir = System.getProperty("test.src");
            Iterable<? extends JavaFileObject> files =
                fm.getJavaFileObjectsFromFiles(Arrays.asList(new File(srcdir, "T6265137a.java")));
            javac.getTask(null, fm, dl,
                          Arrays.asList("-target", Integer.toString(Runtime.version().feature())),
                          null, files).call();
        }
    }
}
