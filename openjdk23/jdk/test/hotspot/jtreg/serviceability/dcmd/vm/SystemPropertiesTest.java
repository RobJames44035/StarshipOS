/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import org.testng.annotations.Test;

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.dcmd.CommandExecutor;
import jdk.test.lib.dcmd.JMXExecutor;

/*
 * @test
 * @summary Test of diagnostic command VM.system_properties
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.compiler
 *          java.management
 *          jdk.internal.jvmstat/sun.jvmstat.monitor
 * @run testng SystemPropertiesTest
 */
public class SystemPropertiesTest {
    private final static String PROPERTY_NAME  = "SystemPropertiesTestPropertyName";
    private final static String PROPERTY_VALUE = "SystemPropertiesTestPropertyValue";

    public void run(CommandExecutor executor) {
        System.setProperty(PROPERTY_NAME, PROPERTY_VALUE);

        OutputAnalyzer output = executor.execute("VM.system_properties");
        output.shouldContain(PROPERTY_NAME + "=" + PROPERTY_VALUE);
    }

    @Test
    public void jmx() {
        run(new JMXExecutor());
    }
}
