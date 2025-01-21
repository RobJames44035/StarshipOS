/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8319311
 * @summary Tests JdiStarter
 * @modules jdk.jshell/jdk.jshell jdk.jshell/jdk.jshell.spi jdk.jshell/jdk.jshell.execution
 * @run testng JdiStarterTest
 */

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.testng.annotations.Test;
import jdk.jshell.JShell;
import jdk.jshell.SnippetEvent;
import jdk.jshell.execution.JdiDefaultExecutionControl.JdiStarter;
import jdk.jshell.execution.JdiDefaultExecutionControl.JdiStarter.TargetDescription;
import jdk.jshell.execution.JdiExecutionControlProvider;
import jdk.jshell.execution.JdiInitiator;
import static org.testng.Assert.assertEquals;

@Test
public class JdiStarterTest {

    public void jdiStarter() {
        // turn on logging of launch failures
        Logger.getLogger("jdk.jshell.execution").setLevel(Level.ALL);
        JdiStarter starter = (env, parameters, port) -> {
            assertEquals(parameters.get(JdiExecutionControlProvider.PARAM_HOST_NAME), "");
            assertEquals(parameters.get(JdiExecutionControlProvider.PARAM_LAUNCH), "false");
            assertEquals(parameters.get(JdiExecutionControlProvider.PARAM_REMOTE_AGENT), "jdk.jshell.execution.RemoteExecutionControl");
            assertEquals(parameters.get(JdiExecutionControlProvider.PARAM_TIMEOUT), "5000");
            JdiInitiator jdii =
                    new JdiInitiator(port,
                                     env.extraRemoteVMOptions(),
                                     "jdk.jshell.execution.RemoteExecutionControl",
                                     false,
                                     null,
                                     5000,
                                     Collections.emptyMap());
            return new TargetDescription(jdii.vm(), jdii.process());
        };
        JShell jshell =
                JShell.builder()
                      .executionEngine(new JdiExecutionControlProvider(starter), Map.of())
                      .build();
        List<SnippetEvent> evts = jshell.eval("1 + 2");
        assertEquals(1, evts.size());
        assertEquals("3", evts.get(0).value());
    }
}
