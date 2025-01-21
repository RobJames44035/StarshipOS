/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package jdk.test.lib;

import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * Auxiliary class to run target w/ given timeout.
 */
public class TimeLimitedRunner implements Callable<Void> {
    private final long              stoptime;
    private final long              timeout;
    private final double            factor;
    private final Callable<Boolean> target;

    /**
     * @param timeout   a timeout. zero means no time limitation
     * @param factor    a multiplier used to estimate next iteration time
     * @param target    a target to run
     * @throws NullPointerException     if target is null
     * @throws IllegalArgumentException if timeout is negative or
                                        factor isn't positive
     */
    public TimeLimitedRunner(long timeout, double factor,
            Callable<Boolean> target) {
        Objects.requireNonNull(target, "target must not be null");
        if (timeout < 0) {
            throw new IllegalArgumentException("timeout[" + timeout + "] < 0");
        }
        if (factor <= 0d) {
            throw new IllegalArgumentException("factor[" + factor + "] <= 0");
        }
        this.stoptime = System.currentTimeMillis() + timeout;
        this.timeout = timeout;
        this.factor = factor;
        this.target = target;
    }

    /**
     * Runs @{linkplan target} while it returns true and timeout isn't exceeded
     */
    @Override
    public Void call() throws Exception {
        long maxDuration = 0L;
        long iterStart = System.currentTimeMillis();
        if (timeout != 0 && iterStart > stoptime) {
            return null;
        }
        while (target.call()) {
            if (timeout != 0) {
                long iterDuration = System.currentTimeMillis() - iterStart;
                maxDuration = Math.max(maxDuration, iterDuration);
                iterStart = System.currentTimeMillis();
                if (iterStart + (maxDuration * factor) > stoptime) {
                    System.out.println("Not enough time to continue execution. "
                            + "Interrupted.");
                    break;
                }
            }
        }
        return null;
    }

}
