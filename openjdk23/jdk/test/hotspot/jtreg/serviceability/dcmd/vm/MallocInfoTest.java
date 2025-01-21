/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

import jdk.test.lib.Platform;
import org.testng.annotations.Test;
import jdk.test.lib.dcmd.CommandExecutor;
import jdk.test.lib.dcmd.JMXExecutor;
import jdk.test.lib.process.OutputAnalyzer;

/*
 * @test
 * @summary Test of diagnostic command System.native_heap_info
 * @library /test/lib
 * @requires (os.family=="linux")
 * @modules java.base/jdk.internal.misc
 *          java.compiler
 *          java.management
 *          jdk.internal.jvmstat/sun.jvmstat.monitor
 * @run testng MallocInfoTest
 */
public class MallocInfoTest {
    public void run(CommandExecutor executor) {
        OutputAnalyzer output = executor.execute("System.native_heap_info");
        if (!Platform.isMusl()) {
            output.shouldNotContain("Error: ");
            output.shouldContain("<malloc version=");
        } else {
            output.shouldContain("Error: malloc_info(3) not available.");
        }
        output.reportDiagnosticSummary();
    }

    @Test
    public void jmx() {
        run(new JMXExecutor());
    }
}
