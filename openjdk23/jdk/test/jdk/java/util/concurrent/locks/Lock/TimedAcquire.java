/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/* @test
 * @bug 6241823
 * @summary Repeated timed tryAcquire shouldn't hang.
 */

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class TimedAcquire {
    public static void main(String[] args) throws Exception {
        for (Semaphore sem : new Semaphore[]{ new Semaphore(0),
                                              new Semaphore(0, false),
                                              new Semaphore(0, true)})
            for (int delay : new int[] {0, 1})
                for (int i = 0; i < 3; i++)
                    if (sem.tryAcquire(delay, TimeUnit.MILLISECONDS))
                        throw new Error("Acquired Semaphore with no permits!");
        System.out.println("Done!");
    }
}
