/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */
package nsk.share.jpda;

import nsk.share.TestBug;
import nsk.share.locks.MonitorLockingThread;

/*
 *  StateTestThread sequentially switches its state in following order:
 *  - thread not started
 *  - thread is running
 *  - thread is sleeping
 *  - thread in Object.wait()
 *  - thread wait on java monitor
 *  - thread is finished
 *
 *  To use this class create new instance of StateTestThread and sequentially call method nextState().
 */
public class StateTestThread extends Thread {
    // thread states available through ThreadReference.state()
    public static String stateTestThreadStates[] = { "UNKNOWN", "RUNNING", "SLEEPING", "WAIT", "MONITOR", "ZOMBIE" };

    private Object waitOnObject = new Object();

    public StateTestThread(String name) {
        super(name);
    }

    private volatile boolean isRunning;

    private volatile boolean waitState;

    public int getCurrentState() {
        return currentState;
    }

    private MonitorLockingThread auxiliaryThread = new MonitorLockingThread(this);

    private boolean isExecutedWithErrors;

    private volatile boolean readyToBeBlocked;

    private String errorMessage;

    public void run() {
        isRunning = true;

        // running state
        while (isRunning)
            ;

        try {
            // sleeping state
            sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            // expected exception
        }

        synchronized (waitOnObject) {
            try {
                // wait state
                while (waitState)
                    waitOnObject.wait();
            } catch (InterruptedException e) {
                isExecutedWithErrors = true;
                errorMessage = "StateTestThread was unexpected interrupted during waiting";
            }
        }

        // start auxiliary thread which should acquire 'this' lock
        auxiliaryThread.acquireLock();

        readyToBeBlocked = true;

        // try acquire the same lock as auxiliaryThread, switch state to 'wait on monitor'
        synchronized (this) {

        }
    }

    private int currentState = 1;

    public void nextState() {
        // check is thread states change as expected
        if (isExecutedWithErrors)
            throw new TestBug(errorMessage);

        switch (currentState++) {
        case 1:
            // start thread
            start();

            while (!isRunning)
                Thread.yield();

            break;
        case 2:
            // stop running
            isRunning = false;

            while (this.getState() != Thread.State.TIMED_WAITING)
                Thread.yield();

            break;
        case 3:
            waitState = true;

            // stop sleeping
            interrupt();

            while (getState() != Thread.State.WAITING)
                Thread.yield();

            break;
        case 4:
            waitState = false;

            // stop wait
            synchronized (waitOnObject) {
                waitOnObject.notify();
            }

            while (!readyToBeBlocked || (getState() != Thread.State.BLOCKED))
                Thread.yield();

            break;
        case 5:
            // let StateTestThread thread acquire lock
            auxiliaryThread.releaseLock();
            try {
                join();
            } catch (InterruptedException e) {
                throw new TestBug("Unexpected exception: " + e);
            }
            break;

        default:
            throw new TestBug("Invalid thread state");
        }
    }
}
