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
 * @bug 8221730
 * @summary Test of diagnostic command VM.version (tests all DCMD executors)
 * @modules java.base/jdk.internal.misc
 *          java.base/jdk.internal.module
 *          java.compiler
 *          java.management
 *          jdk.internal.jvmstat/sun.jvmstat.monitor
 * @library /test/lib
 *          /vmTestbase
 * @requires vm.flagless
 * @run testng/othervm -XX:+UsePerfData VMVersionTest
 */
public class VMVersionTest {

    private static final String TEST_PROCESS_CLASS_NAME = process.TestJavaProcess.class.getName();

    public void run(CommandExecutor executor) {
        OutputAnalyzer output = executor.execute("VM.version");
        output.shouldMatch(".*(?:HotSpot|OpenJDK).*VM.*");
    }

    @Test
    public void pid() {
        run(new PidJcmdExecutor());
    }

    @Test
    public void mainClass() {
        TestProcessLauncher t = new TestProcessLauncher(TEST_PROCESS_CLASS_NAME);
        try {
            t.launch();
            run(new MainClassJcmdExecutor(TEST_PROCESS_CLASS_NAME));
        } finally {
            t.quit();
        }
    }

    @Test
    public void mainClassForJar() {
        TestProcessJarLauncher t = new TestProcessJarLauncher(TEST_PROCESS_CLASS_NAME);
        try {
            t.launch();
            String jarFile = t.getJarFile();
            run(new MainClassJcmdExecutor(jarFile));
        } finally {
            t.quit();
        }
    }

    @Test
    public void mainClassForModule() {
        TestProcessModuleLauncher t = new TestProcessModuleLauncher(TEST_PROCESS_CLASS_NAME);
        try {
            t.launch();
            String moduleName = t.getModuleName();
            run(new MainClassJcmdExecutor(moduleName));
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

}
