/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8169519 8166581
 * @summary Tests for JDI connector failure
 * @modules jdk.jshell/jdk.jshell jdk.jshell/jdk.jshell.spi jdk.jshell/jdk.jshell.execution
 * @run testng JdiBadOptionLaunchExecutionControlTest
 */

import java.util.logging.Level;
import java.util.logging.Logger;
import org.testng.annotations.Test;
import jdk.jshell.JShell;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

@Test
public class JdiBadOptionLaunchExecutionControlTest {

    private static final String EXPECTED_ERROR =
            "Launching JShell execution engine threw: Failed remote launch: java.util.concurrent.ExecutionException: com.sun.jdi.connect.VMStartException: VM initialization failed";

    public void badOptionLaunchTest() {
        try {
            // turn on logging of launch failures
            Logger.getLogger("jdk.jshell.execution").setLevel(Level.ALL);
            JShell.builder()
                    .executionEngine("jdi:launch(true)")
                    .remoteVMOptions("-BadBadOption")
                    .build();
        } catch (IllegalStateException ex) {
            assertTrue(ex.getMessage().startsWith(EXPECTED_ERROR), ex.getMessage());
            return;
        }
        fail("Expected IllegalStateException");
    }
}
