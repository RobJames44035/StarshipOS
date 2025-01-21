/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.dcmd.CommandExecutor;
import jdk.test.lib.dcmd.JMXExecutor;
import jdk.test.lib.dcmd.PidJcmdExecutor;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * @test
 * @bug 8054890
 * @summary Test of JVMTI.data_dump diagnostic command
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 * @run testng DataDumpDcmdTest
 */

/**
 * This test issues the "JVMTI.data_dump" command which will dump the related JVMTI
 * data.
 *
 */
public class DataDumpDcmdTest {
    public void run(CommandExecutor executor) {
        OutputAnalyzer out = executor.execute("JVMTI.data_dump");

        // stderr should be empty except for VM warnings.
        if (!out.getStderr().isEmpty()) {
            List<String> lines = Arrays.asList(out.getStderr().split("(\\r\\n|\\n|\\r)"));
            Pattern p = Pattern.compile(".*VM warning.*");
            for (String line : lines) {
                Matcher m = p.matcher(line);
                if (!m.matches()) {
                    throw new RuntimeException("Stderr has output other than VM warnings");
                }
            }
        }
    }

    @Test
    public void jmx() throws Throwable {
        run(new JMXExecutor());
    }

    @Test
    public void cli() throws Throwable {
        run(new PidJcmdExecutor());
    }
}
