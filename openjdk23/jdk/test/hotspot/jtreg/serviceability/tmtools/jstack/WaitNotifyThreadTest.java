/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @summary  Call Object.wait() method. Check that monitor information
 *           presented in the stack is correct. Call notifyAll method
 *           monitor info have to disappear from the stack.
 *           Repeats the same scenario calling interrupt() method
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 * @library ../share
 * @run main/othervm -XX:+UsePerfData WaitNotifyThreadTest
 */
import common.ToolResults;
import java.util.Iterator;
import utils.*;

public class WaitNotifyThreadTest {

    private Object monitor = new Object();
    private final String OBJECT = "a java.lang.Object";
    private final String OBJECT_WAIT = "java.lang.Object.wait0";
    private final String RUN_METHOD = "WaitNotifyThreadTest$WaitThread.run";

    interface Action {
        void doAction(Thread thread);
    }

    class ActionNotify implements Action {

        @Override
        public void doAction(Thread thread) {
            // Notify the waiting thread, so it stops waiting and sleeps
            synchronized (monitor) {
                monitor.notifyAll();
            }
            // Wait until MyWaitingThread exits the monitor and sleeps
            while (thread.getState() != Thread.State.TIMED_WAITING) {}
        }
    }

    class ActionInterrupt implements Action {

        @Override
        public void doAction(Thread thread) {
            // Interrupt the thread
            thread.interrupt();
            // Wait until MyWaitingThread exits the monitor and sleeps
            while (thread.getState() != Thread.State.TIMED_WAITING) {}
        }
    }

    class WaitThread extends Thread {

        @Override
        public void run() {
            try {
                synchronized (monitor) {
                    monitor.wait();
                }
            } catch (InterruptedException x) {

            }
            Utils.sleep();
        }
    }

    public static void main(String[] args) throws Exception {
        new WaitNotifyThreadTest().doTest();
    }

    private void doTest() throws Exception {

        // Verify stack trace consistency when notifying the thread
        doTest(new ActionNotify());

        // Verify stack trace consistency when interrupting the thread
        doTest(new ActionInterrupt());
    }

    private void doTest(Action action) throws Exception {

        final String WAITING_THREAD_NAME = "MyWaitingThread";

        // Start a thread that just waits
        WaitThread waitThread = new WaitThread();
        waitThread.setName(WAITING_THREAD_NAME);
        waitThread.start();
        // Wait until MyWaitingThread enters the monitor
        while (waitThread.getState() != Thread.State.WAITING) {}

        // Collect output from the jstack tool
        JstackTool jstackTool = new JstackTool(ProcessHandle.current().pid());
        ToolResults results = jstackTool.measure();

        // Analyze the jstack output for the patterns needed
        JStack jstack1 = new DefaultFormat().parse(results.getStdoutString());
        ThreadStack ti1 = jstack1.getThreadStack(WAITING_THREAD_NAME);
        analyzeThreadStackWaiting(ti1);

        action.doAction(waitThread);

        // Collect output from the jstack tool again
        results = jstackTool.measure();

        // Analyze the output again
        JStack jstack2 = new DefaultFormat().parse(results.getStdoutString());
        ThreadStack ti2 = jstack2.getThreadStack(WAITING_THREAD_NAME);
        analyzeThreadStackNoWaiting(ti2);
    }

    private void analyzeThreadStackWaiting(ThreadStack ti1) {
        Iterator<MethodInfo> it = ti1.getStack().iterator();

        String monitorAddress = null;
        while (it.hasNext()) {
            MethodInfo mi = it.next();
            if (mi.getName().startsWith(OBJECT_WAIT) && mi.getCompilationUnit() == null /*native method*/) {
                if (mi.getLocks().size() == 1) {
                    MonitorInfo monInfo = mi.getLocks().getFirst();
                    monitorAddress = monInfo.getMonitorAddress();
                    assertMonitorInfo("waiting on", monInfo, monitorAddress, OBJECT_WAIT);
                } else {
                    throw new RuntimeException(OBJECT_WAIT + " method has to contain one lock record but it contains "
                                               + mi.getLocks().size());
                }
            }

            if (mi.getName().startsWith(RUN_METHOD)) {
                if (monitorAddress == null) {
                    throw new RuntimeException("Cannot found monitor info associated with " + OBJECT_WAIT + " method");
                }
                if (mi.getLocks().size() == 1) {
                    MonitorInfo monInfo = mi.getLocks().getLast();
                    if (monitorAddress.equals("no object reference available")) {
                        monitorAddress = monInfo.getMonitorAddress();
                    }
                    assertMonitorInfo("locked", monInfo, monitorAddress, RUN_METHOD);
                }
                else {
                    throw new RuntimeException(RUN_METHOD + " method has to contain one lock record but it contains "
                                               + mi.getLocks().size());
                }
            }
        }
    }

    private void assertMonitorInfo(String expectedMessage, MonitorInfo monInfo, String monitorAddress, String method) {
        if (monInfo.getType().equals(expectedMessage)
                && compareMonitorClass(monInfo)
                && monInfo.getMonitorAddress().equals(
                        monitorAddress)) {
            System.out.println("Correct monitor info found in " + method + " method");
        } else {
            System.err.println("Error: incorrect monitor info: " + monInfo.getType() + ", " + monInfo.getMonitorClass() + ", " + monInfo.getMonitorAddress());
            System.err.println("Expected: " + expectedMessage + ", a java.lang.Object, " + monitorAddress);
            throw new RuntimeException("Incorrect lock record in " + method + " method");
        }
    }

    private boolean compareMonitorClass(MonitorInfo monInfo) {
        // If monitor class info is present in the jstack output
        // then compare it with the class of the actual monitor object
        // If there is no monitor class info available then return true
        return OBJECT.equals(monInfo.getMonitorClass()) || (monInfo.getMonitorClass() == null);
    }

    private void analyzeThreadStackNoWaiting(ThreadStack ti2) {
        Iterator<MethodInfo> it = ti2.getStack().iterator();

        while (it.hasNext()) {
            MethodInfo mi = it.next();
            if (mi.getLocks().size() != 0) {
                throw new RuntimeException("Unexpected lock record in "
                        + mi.getName() + " method");
            }
        }
    }

}
