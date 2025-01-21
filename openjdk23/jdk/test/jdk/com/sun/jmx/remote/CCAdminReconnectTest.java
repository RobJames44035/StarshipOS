/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 7009998
 * @summary Tests the correct processing of concurrent ClientComunicatorAdmin reconnect requests.
 * @author Jaroslav Bachorik
 * @modules java.management/com.sun.jmx.remote.internal
 * @run clean CCAdminReconnectTest
 * @run build CCAdminReconnectTest
 * @run main CCAdminReconnectTest
 */

import com.sun.jmx.remote.internal.ClientCommunicatorAdmin;
import java.io.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class CCAdminReconnectTest {
    final private static int THREAD_COUNT = 3;

    public static void main(String ... args) throws Exception {
        final ExecutorService e = Executors.newFixedThreadPool(THREAD_COUNT);
        final AtomicBoolean beingReconnected = new AtomicBoolean();
        final Collection<Exception> thrownExceptions = new LinkedList<>();

        System.out.println(": Testing concurrent restart of ClientCommunicatorAdmin");

        final ClientCommunicatorAdmin cca = new ClientCommunicatorAdmin(50) {

            @Override
            protected void checkConnection() throws IOException {
                // empty
            }

            @Override
            protected void doStart() throws IOException {
                if (!beingReconnected.compareAndSet(false, true)) {
                    IOException e = new IOException("Detected overlayed reconnect requests");
                    thrownExceptions.add(e);
                    throw e;
                }
                try {
                    Thread.sleep(800); // simulating a workload
                    beingReconnected.set(false);
                } catch (InterruptedException e) {
                }
            }

            @Override
            protected void doStop() {
                // empty
            }
        };

        Runnable r = new Runnable() {
            final private IOException e = new IOException("Forced reconnect");
            @Override
            public void run() {
                try {
                    // forcing the reconnect request
                    cca.gotIOException(e);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        System.out.println(": Spawning " + THREAD_COUNT + " concurrent reconnect requests");

        for(int i=0;i<THREAD_COUNT;i++) {
            e.execute(r);
        }

        Thread.sleep(THREAD_COUNT * 1000);
        e.shutdown();
        e.awaitTermination(10, TimeUnit.SECONDS);

        cca.terminate();

        for(Exception thrown : thrownExceptions) {
            throw thrown;
        }
        System.out.println(": Requests processed successfully");
    }
}

