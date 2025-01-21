/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
/*
 * @test
 * @bug 8081474
 * @summary  Verifies if SwingWorker calls 'done'
 *           before the 'doInBackground' is finished
 * @run main TestDoneBeforeDoInBackground
 */
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.SwingWorker;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class TestDoneBeforeDoInBackground {

    private static final int WAIT_TIME = 200;
    private static final long CLEANUP_TIME = 1000;

    private static final AtomicBoolean doInBackgroundStarted = new AtomicBoolean(false);
    private static final AtomicBoolean doInBackgroundFinished = new AtomicBoolean(false);
    private static final AtomicBoolean doneFinished = new AtomicBoolean(false);
    private static final CountDownLatch doneLatch = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {
        SwingWorker<String, String> worker = new SwingWorker<>() {
            @Override
            protected String doInBackground() throws Exception {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        System.out.println("Working...");
                        Thread.sleep(WAIT_TIME);
                    }
                } catch (InterruptedException ex) {
                    System.out.println("Got interrupted!");
                }

                try {
                    doInBackgroundStarted.set(true);
                    System.out.println("Cleaning up");
                    Thread.sleep(CLEANUP_TIME);
                    System.out.println("Done cleaning");
                    doInBackgroundFinished.set(true);
                } catch (InterruptedException ex) {
                    System.out.println("Got interrupted second time!");
                }

                return null;
            }

            @Override
            protected void done() {
                if (!doInBackgroundFinished.get()) {
                    throw new RuntimeException("done called before " +
                                               "doInBackground is finished");
                }
                System.out.println("Done");
                doneFinished.set(true);
                doneLatch.countDown();
            }
        };

        worker.addPropertyChangeListener(
            new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    System.out.println("doInBackgroundStarted: " +
                                        doInBackgroundStarted.get() +
                                        " doInBackgroundFinished: " +
                                        doInBackgroundFinished.get() +
                                        " done: " + doneFinished.get() +
                                        " state: " + worker.getState());
                    // Before doInBackground method is invoked,
                    // SwingWorker notifies PropertyChangeListeners about the
                    // property change to StateValue.STARTED
                    if (doInBackgroundStarted.get()
                        && worker.getState() == SwingWorker.StateValue.STARTED) {
                        throw new RuntimeException(
                               "PropertyChangeListeners called with " +
                               "state STARTED after doInBackground is invoked");
                    }

                    // Ensure DONE state is notified AFTER
                    // doInBackground is finished AND done is invoked
                    if (doInBackgroundFinished.get() && !doneFinished.get()
                        && worker.getState() == SwingWorker.StateValue.DONE) {
                        throw new RuntimeException(
                              "done method is NOT executed but state is DONE");
                    }

                    // After the doInBackground method is finished
                    // SwingWorker notifies PropertyChangeListeners
                    // property change to StateValue.DONE
                    if (worker.getState() != SwingWorker.StateValue.DONE
                        && doInBackgroundFinished.get()) {
                        throw new RuntimeException(
                            "PropertyChangeListeners called after " +
                            "doInBackground is finished but before State changed to DONE");
                    }
                }
            });
        worker.execute();
        Thread.sleep(WAIT_TIME * 3);

        final long start = System.currentTimeMillis();
        worker.cancel(true);
        final long end = System.currentTimeMillis();

        if ((end - start) > 100) {
            throw new RuntimeException("Cancel took too long: "
                                       + ((end - start) / 1000.0d) + " s");
        }
        if (!doneLatch.await(CLEANUP_TIME + 1000L, TimeUnit.MILLISECONDS)) {
            throw new RuntimeException("done didn't complete in time");
        }
        System.out.println("doInBackground " + doInBackgroundFinished.get() +
                           " getState " + worker.getState());
        if (worker.getState() != SwingWorker.StateValue.DONE
            && doInBackgroundFinished.get()) {
            throw new RuntimeException("doInBackground is finished " +
                                       "but State is not DONE");
        }
    }
}


