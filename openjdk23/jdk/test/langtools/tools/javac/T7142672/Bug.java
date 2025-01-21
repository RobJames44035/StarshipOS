/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 7142672
 * @summary Problems with the value passed to the 'classes' param of JavaCompiler.CompilationTask.getTask(...)
 * @author holmlund
 * @modules java.compiler
 *          jdk.compiler
 * @compile AnnoProcessor.java Bug.java Test3.java
 * @run main Bug Test2.java
 * @run main Bug Test2.foo
 * @run main Bug Test3.java
 */
import java.io.*;
import java.util.*;
import javax.tools.*;

// Each run should output the 'could not find class file' message, and not throw an AssertError.
public class Bug {
    public static void main(String... arg) throws Throwable {
        String name = arg[0];
        final String expectedMsg = "error: Could not find class file for '" + name + "'.";
        JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
        JavaCompiler.CompilationTask task2;
        StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw);


        DiagnosticListener<? super javax.tools.JavaFileObject> dl =
            new DiagnosticListener<javax.tools.JavaFileObject>() {
            public void report(Diagnostic message) {
                pw.print("Diagnostic:\n"+ message.toString()+"\n");
                if (!message.toString().equals(expectedMsg)){
                    System.err.println("Diagnostic:\n"+ message.toString()+"\n");
                    System.err.println("--Failed: Unexpected diagnostic");
                    System.exit(1);
                }
            }
        };

        try (StandardJavaFileManager sjfm = javac.getStandardFileManager(dl,null,null)) {

            List<String> opts = new ArrayList<String>();
            opts.add("-proc:only");
            opts.add("-processor");
            opts.add("AnnoProcessor");

            boolean xxx;

            System.err.println("\n-- " + name);
            task2 = javac.getTask(pw, sjfm, dl, opts, Arrays.asList(name), null);
            xxx = task2.call();

            String out = sw.toString();
            System.err.println(out);
            if (out.contains("Assert")) {
                System.err.println("--Failed: Assertion failure");
                System.exit(1);
            }
            if (!out.contains(expectedMsg)) {
                System.err.println("--Failed: Expected diagnostic not found");
                System.exit(1);
            }
        }
    }
}
