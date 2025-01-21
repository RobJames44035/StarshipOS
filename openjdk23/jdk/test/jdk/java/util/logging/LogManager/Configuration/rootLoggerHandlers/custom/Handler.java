/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */
package custom;

import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author danielfuchs
 */
public class Handler extends java.util.logging.ConsoleHandler {

    static final AtomicLong IDS = new AtomicLong();
    public final long id = IDS.incrementAndGet();
    public Handler() {
        System.out.println("Handler(" + id + ") created");
    }

    @Override
    public void close() {
        System.out.println("Handler(" + id + ") closed");
        super.close();
    }

    @Override
    public String toString() {
        return this.getClass().getName() + '(' + id + ')';
    }
}
