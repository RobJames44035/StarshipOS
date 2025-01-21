/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug 6578538 8027624
 * @library /test/lib
 * @summary com.sun.crypto.provider.SunJCE instance leak using KRB5 and
 *     LoginContext
 * @author Brad Wetmore
 *
 * @run main/othervm -Xmx20m TestProviderLeak
 *
 */

/*
 * We force the leak to become a problem by eating up most JVM free memory.
 * In current runs on a server and client machine, it took roughly 50-150
 * iterations to have the memory leak or time-out shut down other operations.
 * It complained about "JCE cannot authenticate the provider SunJCE" or timed
 * out.
 */

import javax.crypto.*;
import javax.crypto.spec.*;

import java.util.*;
import java.util.concurrent.*;
import jdk.test.lib.security.SecurityUtils;

public class TestProviderLeak {
    private static final int MB = 1024 * 1024;
    // Currently, 3MB heap size is reserved for running testing iterations.
    // It is tweaked to make sure the test quickly triggers the memory leak
    // or throws out TimeoutException.
    private static final int RESERVATION = 3;
    // The maximum time, 5 seconds, to wait for each iteration.
    private static final int TIME_OUT;
    static {
        int timeout = 5;
        try {
            double timeoutFactor = Double.parseDouble(
                    System.getProperty("test.timeout.factor", "1.0"));
            timeout = (int) (timeout * timeoutFactor);
        } catch (Exception e) {
            System.out.println("Warning: " + e);
        }
        TIME_OUT = timeout;
        System.out.println("Timeout for each iteration is "
                + TIME_OUT + " seconds");
    }

    private static Deque<byte []> eatupMemory() throws Exception {
        dumpMemoryStats("Before memory allocation");

        Deque<byte []> data = new ArrayDeque<byte []>();
        boolean hasException = false;
        while (!hasException) {
            byte [] megaByte;
            try {
                megaByte = new byte [MB];
                data.add(megaByte);
            } catch (OutOfMemoryError e) {
                megaByte = null;    // Free memory ASAP

                int size = data.size();

                for (int j = 0; j < RESERVATION && !data.isEmpty(); j++) {
                    data.removeLast();
                }
                System.gc();
                hasException = true;
                System.out.println("OOME is thrown when allocating "
                        + size + "MB memory.");
            }
        }
        dumpMemoryStats("After memory allocation");

        return data;
    }

    private static void dumpMemoryStats(String s) throws Exception {
        Runtime rt = Runtime.getRuntime();
        System.out.println(s + ":\t"
            + rt.freeMemory() + " bytes free");
    }

    public static void main(String [] args) throws Exception {
        // Prepare the test
        final SecretKeyFactory skf =
            SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1",
                    System.getProperty("test.provider.name", "SunJCE"));
        final PBEKeySpec pbeKS = new PBEKeySpec(
            "passPhrase".toCharArray(), new byte [SecurityUtils.getTestSaltSize()], 1000, 512);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<SecretKey> task = new Callable<SecretKey>() {
            @Override
            public SecretKey call() throws Exception {
                return skf.generateSecret(pbeKS);
            }
        };

        // Eat up memory
        Deque<byte []> dummyData = eatupMemory();
        assert (dummyData != null);

        // Start testing iteration
        try {
            for (int i = 0; i <= 1000; i++) {
                if ((i % 20) == 0) {
                    // Calling gc() isn't dependable, but doesn't hurt.
                    // Gives better output in leak cases.
                    System.gc();
                    dumpMemoryStats("Iteration " + i);
                }

                Future<SecretKey> future = executor.submit(task);

                try {
                    future.get(TIME_OUT, TimeUnit.SECONDS);
                } catch (Exception e) {
                    dumpMemoryStats("\nException seen at iteration " + i);
                    throw e;
                }
            }
        } finally {
            // JTReg will time out after two minutes. Proactively release
            // the memory to avoid JTReg time-out situation.
            dummyData = null;
            System.gc();
            dumpMemoryStats("Memory dereference");
            executor.shutdownNow();
        }
    }
}
