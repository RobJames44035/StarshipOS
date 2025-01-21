/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8131029 8160127 8159935 8169519
 * @summary Test that fail-over works for fail-over ExecutionControl generators.
 * @modules jdk.jshell/jdk.jshell.execution
 *          jdk.jshell/jdk.jshell.spi
 * @build KullaTesting ExecutionControlTestBase
 * @run testng FailOverExecutionControlHangingLaunchTest
 */

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

@Test
public class FailOverExecutionControlHangingLaunchTest extends ExecutionControlTestBase {

    @BeforeMethod
    @Override
    public void setUp() {
        setUp(builder -> builder.executionEngine(
                "failover:0(jdi:remoteAgent(HangingRemoteAgent),launch(true)), "
                        + alwaysPassingSpec()));
    }
}
