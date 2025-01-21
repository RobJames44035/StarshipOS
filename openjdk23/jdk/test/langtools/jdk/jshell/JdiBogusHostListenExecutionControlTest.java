/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8169519 8168615 8176474
 * @summary Tests for JDI connector failure
 * @modules jdk.jshell/jdk.jshell jdk.jshell/jdk.jshell.spi jdk.jshell/jdk.jshell.execution
 * @run testng JdiBogusHostListenExecutionControlTest
 */

import java.util.logging.Level;
import java.util.logging.Logger;
import org.testng.annotations.Test;
import jdk.jshell.JShell;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

@Test
public class JdiBogusHostListenExecutionControlTest {

    private static final String EXPECTED_ERROR =
            "Launching JShell execution engine threw: Failed remote listen:";
    private static final String EXPECTED_LOCATION =
            "@ com.sun.jdi.SocketListen";

    public void badOptionListenTest() {
        try {
            // turn on logging of launch failures
            Logger.getLogger("jdk.jshell.execution").setLevel(Level.ALL);
            JShell.builder()
                    .executionEngine("jdi:hostname(BattyRumbleBuckets-Snurfle-99-Blip)")
                    .build();
        } catch (IllegalStateException ex) {
            assertTrue(ex.getMessage().startsWith(EXPECTED_ERROR),
                    ex.getMessage() + "\nExpected: " + EXPECTED_ERROR);
            assertTrue(ex.getMessage().contains(EXPECTED_LOCATION),
                    ex.getMessage() + "\nExpected: " + EXPECTED_LOCATION);
            return;
        }
        fail("Expected IllegalStateException");
    }
}
