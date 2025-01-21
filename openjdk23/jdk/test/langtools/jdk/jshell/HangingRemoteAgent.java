/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.jshell.JShell;
import jdk.jshell.execution.JdiExecutionControlProvider;
import jdk.jshell.execution.RemoteExecutionControl;
import jdk.jshell.spi.ExecutionControlProvider;

/**
 * HangingRemoteAgent main() runs in its loop for 2X the timeout
 * we give the launcher to fail to attach.
 */
class HangingRemoteAgent extends RemoteExecutionControl {

    private static float timeoutFactor = Float.parseFloat(System.getProperty("test.timeout.factor", "1.0"));

    private static final int TIMEOUT = (int)(2000 * timeoutFactor);
    private static final long DELAY = TIMEOUT * 2L;
    private static final boolean INFRA_VERIFY = false;

    public static void main(String[] args) throws Exception {
        if (INFRA_VERIFY) {
            RemoteExecutionControl.main(args);
        } else {
            long end = System.currentTimeMillis() + DELAY;
            long remaining;
            while ((remaining = end - System.currentTimeMillis()) > 0L) {
                try {
                    Thread.sleep(remaining);
                } catch (InterruptedException ex) {
                    // loop again
                }
            }
        }
    }

    static JShell state(boolean isLaunch, String host) {
        ExecutionControlProvider ecp = new JdiExecutionControlProvider();
        Map<String,String> pm = ecp.defaultParameters();
        pm.put(JdiExecutionControlProvider.PARAM_REMOTE_AGENT, HangingRemoteAgent.class.getName());
        pm.put(JdiExecutionControlProvider.PARAM_HOST_NAME, host==null? "" : host);
        pm.put(JdiExecutionControlProvider.PARAM_LAUNCH, ""+isLaunch);
        pm.put(JdiExecutionControlProvider.PARAM_TIMEOUT, ""+TIMEOUT);
        // turn on logging of launch failures
        Logger.getLogger("jdk.jshell.execution").setLevel(Level.ALL);
        return JShell.builder()
                .executionEngine(ecp, pm)
                .build();
    }

}
