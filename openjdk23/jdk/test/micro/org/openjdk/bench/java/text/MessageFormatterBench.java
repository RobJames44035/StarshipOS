/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
package org.openjdk.bench.java.text;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OperationsPerInvocation;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(3)
@State(Scope.Benchmark)
public class MessageFormatterBench {

    private Object[][] values;

    @Setup
    public void setup() {
        values = new Object[][]{
                new Object[]{Integer.valueOf(13), "MyDisk1"},
                new Object[]{Float.valueOf(25.6f), "MyDisk2"},
                new Object[]{Double.valueOf(123.89), "MyDisk3"},
                new Object[]{Long.valueOf(1234567), "MyDisk4"},
        };
    }

    private MessageFormat messageFormat = new MessageFormat("There is {0} GB of free space on the {1}.", Locale.ENGLISH);

    @Benchmark
    @OperationsPerInvocation(4)
    public void testMessageFormat(final Blackhole bh) {
        for (Object[] value : values) {
            bh.consume(messageFormat.format(value));
        }
    }

    public static void main(String... args) throws Exception {
        Options opts = new OptionsBuilder().include(MessageFormatterBench.class.getSimpleName()).shouldDoGC(true).build();
        new Runner(opts).run();
    }
}
