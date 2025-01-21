/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

// Try to trigger concurrent dynamic-dump-at-exit processing by creating a race
// between normal VM termination by the last non-daemon thread exiting, and a
// call to Runtime.halt().

public class ExitRace {

    static volatile int terminationPhase = 0;

    public static void main(String [] args) {

        // Need to spawn a new non-daemon thread so the main thread will
        // have time to become the DestroyJavaVM thread.
        Thread runner = new Thread("Runner") {
                public void run() {
                    // This thread will be the one to trigger normal VM termination
                    // when it exits. We first create a daemon thread to call
                    // Runtime.halt() and then wait for it to tell us to exit.

                    Thread daemon = new Thread("Daemon") {
                            public void run() {
                                // Let main thread go
                                terminationPhase = 1;
                                // Spin until main thread is active again
                                while (terminationPhase == 1)
                                    ;
                                Runtime.getRuntime().halt(0); // Normal exit code
                            }
                        };
                    daemon.setDaemon(true);
                    daemon.start();

                    // Wait until daemon is ready
                    while (terminationPhase == 0) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException cantHappen) {
                        }
                    }

                    // Release daemon thread
                    terminationPhase++;
                    // Normal exit
                }
            };
        runner.start();
    }
}
