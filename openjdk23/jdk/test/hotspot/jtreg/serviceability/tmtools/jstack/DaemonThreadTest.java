/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @summary Create daemon and non-deamon threads.
 *          Check the correctness of thread's status from jstack.
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 * @library ../share
 * @run main/othervm -XX:+UsePerfData DaemonThreadTest
 */
import common.ToolResults;
import utils.*;

public class DaemonThreadTest {

    static class NormalThread extends Thread {

        NormalThread() {
            setDaemon(false);
        }

        @Override
        public void run() {
            Utils.sleep();
        }

    }

    static class DaemonThread extends Thread {

        DaemonThread() {
            setDaemon(true);
        }

        @Override
        public void run() {
            Utils.sleep();
        }

    }

    public static void main(String[] args) throws Exception {
        testNoDaemon();
        testDaemon();
    }

    private static void testNoDaemon() throws Exception {
        testThread(new NormalThread(), "");
    }

    private static void testDaemon() throws Exception {
        testThread(new DaemonThread(), "daemon");
    }

    private static void testThread(Thread thread, String expectedType) throws Exception {
        // Start the thread
        thread.start();

        // Run jstack tool and collect the output
        JstackTool jstackTool = new JstackTool(ProcessHandle.current().pid());
        ToolResults results = jstackTool.measure();
        int exitCode = results.getExitCode();
        if (exitCode != 0) {
            throw new RuntimeException("jstack tool failed with an exit code " + exitCode);
        }
        // Analyze the jstack output for the correct thread type
        JStack jstack = new DefaultFormat().parse(results.getStdoutString());
        ThreadStack ti = jstack.getThreadStack(thread.getName());

        if (!ti.getType().trim().equals(expectedType)) {
            throw new RuntimeException("incorrect thread type '" + ti.getType() + "' for the thread '" + thread.getName() + "'");
        }

    }

}
