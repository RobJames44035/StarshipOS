/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/**
 * @test
 * @bug 7105883
 * @summary Ensure that JDWP doesn't crash with a null thread group name
 *
 * @run build TestScaffold VMConnection TargetListener TargetAdapter
 * @run driver NullThreadGroupNameTest
 */
import com.sun.jdi.*;
import com.sun.jdi.connect.*;
import com.sun.jdi.event.*;
import com.sun.jdi.request.*;
import com.sun.jdi.VMDisconnectedException;
import java.util.concurrent.CountDownLatch;
import java.util.*;

class DebugTarget {
    public final static String DEBUG_THREAD_NAME = "DebugThread";

    public static void main(String[] args) throws Exception {
        DebugThread thread = new DebugThread();
        thread.start();
        thread.runningLatch.await();
        breakpointHere();
        thread.breakpointLatch.countDown();
    }

    public static void breakpointHere() {
        System.out.println("Breakpoint finished!");
    }

    static class DebugThread extends Thread {
        final CountDownLatch runningLatch = new CountDownLatch(1);
        final CountDownLatch breakpointLatch = new CountDownLatch(1);

        public DebugThread() {
            super(new ThreadGroup(null), DEBUG_THREAD_NAME);
        }

        public void run() {
            runningLatch.countDown();
            try {
                breakpointLatch.await();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }
}

public class NullThreadGroupNameTest extends TestScaffold {

    NullThreadGroupNameTest(String args[]) {
        super(args);
    }

    public static void main(String[] args) throws Exception {
        new NullThreadGroupNameTest(args).startTests();
    }

    protected void runTests() throws Exception {
        startTo("DebugTarget", "breakpointHere", "()V");

        ThreadReference thread = findThread(DebugTarget.DEBUG_THREAD_NAME);
        assertThreadGroupName(thread.threadGroup(), "");

        listenUntilVMDisconnect();
    }

    private ThreadReference findThread(String name) {
        for (ThreadReference thread : vm().allThreads()) {
            if (name.equals(thread.name())) {
                return thread;
            }
        }
        throw new NoSuchElementException("Couldn't find " + name);
    }

    private void assertThreadGroupName(ThreadGroupReference threadGroup, String expectedName) {
        try {
            String name = threadGroup.name();
            if (!expectedName.equals(name)) {
                throw new AssertionError("Unexpected thread group name '" + name + "'");
            }
        } catch (VMDisconnectedException vmde) {
            throw new AssertionError("Likely JVM crash with null thread group name", vmde);
        }
    }
}
