/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/* @test
 * @bug 4499763
 * @summary Check for race condition between close and available
 */

import java.io.*;

/*
 * Note: this is a probabalistic test and will not always fail on
 * a workspace where the race condition can occur. However it should
 * never fail on a workspace where the bug has been fixed.
 */
public class CloseAndAvailableRC {
    public static void main(final String[] args) throws Exception {
        CloseAndAvailableRC rc = new CloseAndAvailableRC();
        rc.go();
    }

    private PipedInputStream inPipe = null;
    private PipedOutputStream outPipe = null;
    private Thread sink = null;
    private volatile boolean stop = false;
    private volatile boolean stopTest = false;

    private void go() throws Exception {
        for (long i=0; i<2000; i++) {
            if (stopTest) {
                cleanup();
                throw new RuntimeException("Test failed");
            }
            resetPipes();
            System.err.println("Closing...");
            inPipe.close();
        }
        cleanup();
    }

    // Cleanup old threads
    private void cleanup() throws Exception {
        if (sink != null) {
            stop = true;
            sink.interrupt();
            try {
                sink.join();
            } catch (InterruptedException e) {
            }
            stop = false;
            // Input Stream will have been closed already
            outPipe.close();
        }
    }

    private void resetPipes() throws Exception {
        cleanup();

        // Init pipe; Note: output never read
        inPipe = new PipedInputStream();
        outPipe = new PipedOutputStream(inPipe);

        // Put stuff in pipe so that available() > 0
        for (byte b = 0; b < 10; b++)
            outPipe.write(b);

        sink = new Sink();
        sink.start();
    }

    private class Sink extends Thread {
        public void run() {
            while (!stop) {
                try {
                    final int num = inPipe.available();
                    if (num < 0) {
                        // Bug detected; stop the test
                        stopTest = true;
                    }
                } catch (final IOException e) {
                    System.err.println("Error on available:" + e.getMessage());
                    e.printStackTrace(System.err);
                }
            }
        }
    }
}
