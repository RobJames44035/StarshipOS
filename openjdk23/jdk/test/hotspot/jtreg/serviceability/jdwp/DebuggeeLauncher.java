/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

import java.util.StringTokenizer;
import jdk.test.lib.JDWP;
import static jdk.test.lib.Asserts.assertFalse;
import jdk.test.lib.process.ProcessTools;

/**
 * Launches the debuggee with the necessary JDWP options and handles the output
 */
public class DebuggeeLauncher implements StreamHandler.Listener {

    public interface Listener {

        /**
         * Callback to use when a module name is received from the debuggee
         *
         * @param modName module name reported by the debuggee
         */
        void onDebuggeeModuleInfo(String modName);

        /**
         * Callback to use when the debuggee completes sending out the info
         */
        void onDebuggeeSendingCompleted();

    }

    private int jdwpPort = -1;
    private static final String DEBUGGEE = "AllModulesCommandTestDebuggee";
    private static final String JDWP_OPT = "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=0";

    private Process p;
    private final Listener listener;

    /**
     * @param listener the listener we report the debuggee events to
     */
    public DebuggeeLauncher(Listener listener) {
        this.listener = listener;
    }

    /**
     * Starts the debuggee with the necessary JDWP options and handles the
     * debuggee's stdout output. stderr might contain jvm output, which is just printed to the log.
     *
     * @throws Throwable
     */
    public void launchDebuggee() throws Throwable {

        ProcessBuilder pb = ProcessTools.createTestJavaProcessBuilder(JDWP_OPT, DEBUGGEE);
        p = pb.start();
        StreamHandler inputHandler = new StreamHandler(p.getInputStream(), this);
        StreamHandler errorHandler = new StreamHandler(p.getErrorStream(), l -> System.out.println("[stderr]: " + l));
        inputHandler.start();
        errorHandler.start();
    }

    /**
     * Terminates the debuggee
     */
    public void terminateDebuggee() {
        if (p.isAlive()) {
            p.destroyForcibly();
        }
    }

    /**
     * Gets JDWP port debuggee is listening on.
     *
     * @return JDWP port
     */
    public int getJdwpPort() {
        assertFalse(jdwpPort == -1, "JDWP port is not detected");
        return jdwpPort;
    }

    @Override
    public void onStringRead(String line) {
        System.out.println("[stdout]: " + line);
        if (jdwpPort == -1) {
            JDWP.ListenAddress addr = JDWP.parseListenAddress(line);
            if (addr != null) {
                jdwpPort = Integer.parseInt(addr.address());
            }
        }
        StringTokenizer st = new StringTokenizer(line);
        String token = st.nextToken();
        switch (token) {
            case "module":
                listener.onDebuggeeModuleInfo(st.nextToken());
                break;
            case "ready":
                listener.onDebuggeeSendingCompleted();
                break;
        }
    }
}
