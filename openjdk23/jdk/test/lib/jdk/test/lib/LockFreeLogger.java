/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package jdk.test.lib;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

/**
 * A logger designed specifically to allow collecting ordered log messages
 * in a multi-threaded environment without involving any kind of locking.
 * <p>
 * It is particularly useful in situations when one needs to assert various
 * details about the tested thread state or the locks it hold while also wanting
 * to produce diagnostic log messages.
 */
public class LockFreeLogger {
    /**
     * ConcurrentLinkedQueue implements non-blocking algorithm.
     */
    private final Queue<String> records = new ConcurrentLinkedQueue<>();

    public LockFreeLogger() {
    }

    /**
     * Logs a message.
     * @param format Message format
     * @param params Message parameters
     */
    public void log(String format, Object ... params) {
        records.add(String.format(format, params));
    }

    /**
     * Generates an aggregated log of chronologically ordered messages.
     *
     * @return An aggregated log of chronologically ordered messages
     */
    @Override
    public String toString() {
        return records.stream().collect(Collectors.joining());
    }
}
