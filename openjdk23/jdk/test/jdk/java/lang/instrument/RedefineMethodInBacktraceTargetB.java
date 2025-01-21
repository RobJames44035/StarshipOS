/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/**
 * The first version of this class. The second version is in
 * RedefineMethodInBacktraceTargetB_2.java.
 */
public class RedefineMethodInBacktraceTargetB {
    public static void methodToRedefine() {
        try {
            // signal that we are here
            RedefineMethodInBacktraceApp.called.countDown();

            // wait until test is done
            RedefineMethodInBacktraceApp.stop.await();
        } catch (InterruptedException ex) {
            // ignore, test will fail
        }
    }

    public static void callMethodToDelete() {
        try {
            // signal that we are here
            RedefineMethodInBacktraceApp.called.countDown();

            // wait until test is done
            RedefineMethodInBacktraceApp.stop.await();
        } catch (InterruptedException ex) {
            // ignore, test will fail
        }
    }
}
