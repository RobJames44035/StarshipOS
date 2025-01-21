/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.dcmd.CommandExecutor;
import jdk.test.lib.dcmd.PidJcmdExecutor;
import jdk.test.lib.dcmd.MainClassJcmdExecutor;
import jdk.test.lib.dcmd.FileJcmdExecutor;
import jdk.test.lib.dcmd.JMXExecutor;
import org.testng.annotations.Test;

/*
 * @test
 * @summary Test of invalid diagnostic command (tests all DCMD executors)
 * @library /test/lib
 *          /vmTestbase
 * @requires vm.flagless
 * @modules java.base/jdk.internal.misc
 *          java.compiler
 *          java.management
 *          jdk.internal.jvmstat/sun.jvmstat.monitor
 * @run testng/othervm -XX:+UsePerfData InvalidCommandTest
 */
public class InvalidCommandTest {

    public void run(CommandExecutor executor) {
        OutputAnalyzer output = executor.execute("asdf");
        output.shouldContain("Unknown diagnostic command");
    }

    @Test
    public void pid() {
        run(new PidJcmdExecutor());
    }

    @Test
    public void mainClass() {
        TestProcessLauncher t = new TestProcessLauncher(Process.class.getName());
        try {
            t.launch();
            run(new MainClassJcmdExecutor(Process.class.getName()));
        } finally {
            t.quit();
        }
    }

    @Test
    public void file() {
        run(new FileJcmdExecutor());
    }

    @Test
    public void jmx() {
        run(new JMXExecutor());
    }

    private static class Process extends process.TestJavaProcess {
    }
}
