/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8169519 8166581
 * @summary Tests for JDI connector failure
 * @modules jdk.jshell/jdk.jshell jdk.jshell/jdk.jshell.spi jdk.jshell/jdk.jshell.execution
 * @run testng JdiBadOptionListenExecutionControlTest
 */

import java.util.logging.Level;
import java.util.logging.Logger;
import org.testng.annotations.Test;
import jdk.jshell.JShell;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

@Test
public class JdiBadOptionListenExecutionControlTest {

    private static final String EXPECTED_ERROR =
            "Unrecognized option: -BadBadOption";

    public void badOptionListenTest() {
        try {
            // turn on logging of launch failures
            Logger.getLogger("jdk.jshell.execution").setLevel(Level.ALL);
            JShell.builder()
                    .executionEngine("jdi")
                    .remoteVMOptions("-BadBadOption")
                    .build();
        } catch (IllegalStateException ex) {
            assertTrue(ex.getMessage().contains(EXPECTED_ERROR), ex.getMessage());
            return;
        }
        fail("Expected IllegalStateException");
    }
}
