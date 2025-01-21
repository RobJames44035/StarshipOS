/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import org.testng.annotations.Test;
import org.testng.Assert;

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.dcmd.CommandExecutor;
import jdk.test.lib.dcmd.JMXExecutor;

import java.text.NumberFormat;
import java.text.ParseException;

/*
 * @test
 * @summary Test of diagnostic command VM.uptime
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.compiler
 *          java.management
 *          jdk.internal.jvmstat/sun.jvmstat.monitor
 * @run testng UptimeTest
 */
public class UptimeTest {
    public void run(CommandExecutor executor) {
        double someUptime = 1.0;
        long startTime = System.currentTimeMillis();
        try {
            synchronized (this) {
                /* Loop to guard against spurious wake ups */
                while (System.currentTimeMillis() < (startTime + someUptime * 1000)) {
                    wait((int) someUptime * 1000);
                }
            }
        } catch (InterruptedException e) {
            Assert.fail("Test error: Exception caught when sleeping:", e);
        }

        OutputAnalyzer output = executor.execute("VM.uptime");

        output.stderrShouldBeEmpty();

        /*
         * Output should be:
         * [pid]:
         * xx.yyy s
         *
         * If there is only one line in output there is no "[pid]:" printout;
         * skip first line, split on whitespace and grab first half
         */
        int index = output.asLines().size() == 1 ? 0 : 1;
        String uptimeString = output.asLines().get(index).split("\\s+")[0];

        try {
            double uptime = NumberFormat.getNumberInstance().parse(uptimeString).doubleValue();
            if (uptime < someUptime) {
                Assert.fail(String.format(
                        "Test failure: Uptime was less than intended sleep time: %.3f s < %.3f s",
                        uptime, someUptime));
            }
        } catch (ParseException e) {
            Assert.fail("Test failure: Could not parse uptime string: " +
                    uptimeString, e);
        }
    }

    @Test
    public void jmx() {
        run(new JMXExecutor());
    }
}
