/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import jdk.test.lib.Platform;
import org.testng.annotations.Test;
import jdk.test.lib.dcmd.CommandExecutor;
import jdk.test.lib.dcmd.JMXExecutor;
import jdk.test.lib.process.OutputAnalyzer;

/*
 * @test
 * @summary Test of diagnostic command VM.trim_libc_heap
 * @library /test/lib
 * @requires os.family == "linux"
 * @modules java.base/jdk.internal.misc
 *          java.compiler
 *          java.management
 *          jdk.internal.jvmstat/sun.jvmstat.monitor
 * @run testng TrimLibcHeapTest
 */
public class TrimLibcHeapTest {
    public void run(CommandExecutor executor) {
        OutputAnalyzer output = executor.execute("System.trim_native_heap");
        output.reportDiagnosticSummary();
        if (Platform.isMusl()) {
            output.shouldContain("Not available");
        } else {
            output.shouldMatch("Trim native heap: RSS\\+Swap: \\d+[BKMG]->\\d+[BKMG] \\([+-]\\d+[BKMG]\\)");
        }
    }

    @Test
    public void jmx() {
        run(new JMXExecutor());
    }
}
