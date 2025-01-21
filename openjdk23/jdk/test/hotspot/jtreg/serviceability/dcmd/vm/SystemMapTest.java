/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

import org.testng.annotations.Test;
import jdk.test.lib.dcmd.CommandExecutor;
import jdk.test.lib.dcmd.JMXExecutor;
import jdk.test.lib.process.OutputAnalyzer;

/*
 * @test id=normal
 * @summary Test of diagnostic command System.map
 * @library /test/lib
 * @requires (vm.gc != "Z") & (os.family == "linux" | os.family == "windows" | os.family == "mac")
 * @modules java.base/jdk.internal.misc
 *          java.compiler
 *          java.management
 *          jdk.internal.jvmstat/sun.jvmstat.monitor
 * @run testng/othervm -XX:+UsePerfData SystemMapTest
 */

/*
 * @test id=zgc
 * @bug 8346717
 * @summary Test of diagnostic command System.map using ZGC
 * @library /test/lib
 * @requires vm.gc.Z & (os.family == "linux" | os.family == "windows" | os.family == "mac")
 * @modules java.base/jdk.internal.misc
 *          java.compiler
 *          java.management
 *          jdk.internal.jvmstat/sun.jvmstat.monitor
 * @run testng/othervm -XX:+UsePerfData -XX:+UseZGC SystemMapTest
 */
public class SystemMapTest extends SystemMapTestBase {
    public void run(CommandExecutor executor) {
        OutputAnalyzer output = executor.execute("System.map");
        boolean NMTOff = output.contains("NMT is disabled");
        for (String s: shouldMatchUnconditionally()) {
            output.shouldMatch(s);
        }
        if (!NMTOff) { // expect VM annotations if NMT is on
            for (String s: shouldMatchIfNMTIsEnabled()) {
                output.shouldMatch(s);
            }
        }
    }

    @Test
    public void jmx() {
        run(new JMXExecutor());
    }
}
