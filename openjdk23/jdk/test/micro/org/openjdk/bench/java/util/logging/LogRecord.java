/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package org.openjdk.bench.java.util.logging;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
public class LogRecord {
    private Logger logger;

    @Setup
    public void setup(final Blackhole bh) {
        try {
            logger = Logger.getLogger("logger");
            logger.addHandler(new Handler() {
                @Override
                public void publish(java.util.logging.LogRecord record) {
                    bh.consume(record);
                }

                @Override
                public void flush() {
                    // do nothing
                }

                @Override
                public void close() throws SecurityException {
                    // do nothing
                }
            });
            logger.setLevel(Level.FINE);
            logger.setUseParentHandlers(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Benchmark
    public void testFine() {
       logger.log(Level.FINE, "test message");
    }

}
