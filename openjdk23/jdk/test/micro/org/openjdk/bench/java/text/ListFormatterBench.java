/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
package org.openjdk.bench.java.text;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.text.ListFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(3)
@State(Scope.Benchmark)
public class ListFormatterBench {

    private List<String> data;

    @Setup
    public void setup() {
        data = List.of("foo", "bar", "baz", "qux", "quux", "quuz");
    }

    private ListFormat listFormat = ListFormat.getInstance();

    @Benchmark
    public String testListFormat() {
        return listFormat.format(data);
    }

    public static void main(String... args) throws Exception {
        Options opts = new OptionsBuilder().include(ListFormatterBench.class.getSimpleName()).shouldDoGC(true).build();
        new Runner(opts).run();
    }
}
