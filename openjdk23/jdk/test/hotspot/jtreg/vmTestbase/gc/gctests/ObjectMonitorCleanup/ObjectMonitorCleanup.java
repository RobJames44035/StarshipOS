/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @key stress
 *
 * @summary converted from VM Testbase gc/gctests/ObjectMonitorCleanup.
 * VM Testbase keywords: [gc, stress, stressopt, nonconcurrent, jrockit]
 * VM Testbase readme:
 * DESCRIPTION
 * Verifies that object monitor objects are cleared
 * out just like PhantomReferences are.
 *
 * COMMENTS
 * This test was ported from JRockit test suite.
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm -XX:-UseGCOverheadLimit gc.gctests.ObjectMonitorCleanup.ObjectMonitorCleanup
 */

package gc.gctests.ObjectMonitorCleanup;

import nsk.share.TestFailure;
import nsk.share.gc.GC;
import nsk.share.gc.GCTestBase;
import nsk.share.test.Stresser;


public class ObjectMonitorCleanup extends GCTestBase {

    /**
     * Verifies that object monitor objects are cleared out
     * just like PhantomReferences are.
     *
     * @return True if successful.
     */
    @Override
    public void run() {
        Stresser stresser = new Stresser(runParams.getStressOptions());
        stresser.start(0);


        MonitorThread mt = new MonitorThread(stresser);
        mt.start();

        try {
            while (stresser.continueExecution()) {
                MonitorThread.otherObject = new byte[(int) (runParams.getTestMemory() / 10000)];
                synchronized (MonitorThread.otherObject) {
                    MonitorThread.otherObject.wait(10);
                }
            }
        } catch (InterruptedException e) {
            synchronized (mt) {
                mt.keepRunning = false;
            }

            try {
                Thread.sleep(runParams.getSleepTime());
            } catch (InterruptedException e1) {
            }

            throw new TestFailure("Problem doing synchronization.");
        }

        try {
            mt.join();

            if (!mt.completedOk) {
                throw new TestFailure("Test thread didn't report "
                        + "successful completion");
            }
        } catch (InterruptedException e) {
            throw new TestFailure("Couldn't wait for thread to finish.");
        }
    }

    public static void main(String[] args) {
        GC.runTest(new ObjectMonitorCleanup(), args);
    }
}
