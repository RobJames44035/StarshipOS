/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug     8214071
 * @summary Broken msg.bug diagnostics when using the compiler API
 * @library lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 * @build ToolTester
 * @run main CrashReport
 */

import com.sun.source.util.JavacTask;
import com.sun.source.util.TaskEvent;
import com.sun.source.util.TaskListener;
import com.sun.tools.javac.api.ClientCodeWrapper.Trusted;
import java.io.File;
import java.io.StringWriter;
import java.util.List;

public class CrashReport extends ToolTester {

    public static void main(String[] args) {
        new CrashReport().test();
    }

    void test() {
        StringWriter pw = new StringWriter();
        JavacTask task =
                (JavacTask)
                        tool.getTask(
                                pw,
                                fm,
                                null,
                                List.of(),
                                null,
                                fm.getJavaFileObjects(new File(test_src, "CrashReport.java")));
        task.addTaskListener(new Listener());
        boolean ok = task.call();
        if (ok) {
            throw new AssertionError("expected compilation to fail");
        }
        String output = pw.toString();
        if (!output.contains("An exception has occurred in the compiler")) {
            throw new AssertionError("expected msg.bug diagnostic, got:\n" + output);
        }
    }

    @Trusted
    static class Listener implements TaskListener {
        @Override
        public void started(TaskEvent e) {
            throw new AssertionError();
        }
    }
}
