/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
package jdk.jfr.internal.util;

import java.util.concurrent.TimeUnit;

public enum TimespanUnit {
    NANOSECONDS ("ns",  TimeUnit.NANOSECONDS, 1000),
    MICROSECONDS("us", TimeUnit.MICROSECONDS, 1000),
    MILLISECONDS("ms", TimeUnit.MILLISECONDS, 1000),
    SECONDS     ("s",       TimeUnit.SECONDS,   60),
    MINUTES     ("m",       TimeUnit.MINUTES,   60),
    HOURS       ("h",         TimeUnit.HOURS,   24),
    DAYS        ("d",          TimeUnit.DAYS,    7);
    public final String text;
    public final long nanos;
    public final int size;
    private final TimeUnit timeUnit;
    TimespanUnit(String text, TimeUnit tu, int size) {
        this.text = text;
        this.nanos = tu.toNanos(1);
        this.size = size;
        this.timeUnit = tu;
    }

    public long toNanos(long value) {
        return timeUnit.toNanos(value);
    }

    public static TimespanUnit fromText(String text) {
        for (TimespanUnit tu : values()) {
            // Case-sensitive by design
            if (tu.text.equals(text)) {
                return tu;
            }
        }
        return null;
    }
}
