/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;

import javax.swing.SwingWorker;

/**
 * @test
 * @bug 6608234
 */
public final class CheckCancellationException {

    private static final CountDownLatch go = new CountDownLatch(1);

    public static void main(final String[] args) throws Exception {
        SwingWorker<?, ?> worker = new SwingWorker() {
            protected Void doInBackground() {
                go.countDown();
                while (!Thread.interrupted()) ;
                return null;
            }
        };
        worker.execute();
        go.await();
        worker.cancel(true);
        try {
            worker.get();
        } catch (final CancellationException expected) {
            // expected exception
            return;
        }
        throw new RuntimeException("CancellationException was not thrown");
    }
}
