/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.dcmd.CommandExecutor;
import jdk.test.lib.dcmd.JMXExecutor;
import org.testng.annotations.Test;

/*
 * @test
 * @summary Test of diagnostic command VM.flags
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.compiler
 *          java.management
 *          jdk.internal.jvmstat/sun.jvmstat.monitor
 * @run testng/othervm -Xmx129m -XX:+UnlockDiagnosticVMOptions -XX:+IgnoreUnrecognizedVMOptions -XX:+ThereShouldNotBeAnyVMOptionNamedLikeThis_Right FlagsTest
 */
public class FlagsTest {
    public void run(CommandExecutor executor) {
        OutputAnalyzer output = executor.execute("VM.flags");

        /* The following are interpreted by the JVM as actual "flags" */
        output.shouldContain("-XX:+UnlockDiagnosticVMOptions");
        output.shouldContain("-XX:+IgnoreUnrecognizedVMOptions");

        /* The following are not */
        output.shouldNotContain("-Xmx129m");
        output.shouldNotContain("-XX:+ThereShouldNotBeAnyVMOptionNamedLikeThis_Right");
    }

    @Test
    public void jmx() {
        run(new JMXExecutor());
    }
}
