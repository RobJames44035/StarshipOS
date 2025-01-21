/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */
package jdk.internal.org.jline.utils;

/**
 * Helper class ti use during I/O operations with an eventual timeout.
 */
public class Timeout {

    private final long timeout;
    private long cur = 0;
    private long end = Long.MAX_VALUE;

    public Timeout(long timeout) {
        this.timeout = timeout;
    }

    public boolean isInfinite() {
        return timeout <= 0;
    }

    public boolean isFinite() {
        return timeout > 0;
    }

    public boolean elapsed() {
        if (timeout > 0) {
            cur = System.currentTimeMillis();
            if (end == Long.MAX_VALUE) {
                end = cur + timeout;
            }
            return cur >= end;
        } else {
            return false;
        }
    }

    public long timeout() {
        return timeout > 0 ? Math.max(1, end - cur) : timeout;
    }
}
