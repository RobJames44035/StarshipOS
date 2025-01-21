/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package org.openjdk.bench.vm.compiler;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.*;
import java.util.concurrent.TimeUnit;

@Warmup(iterations = 3, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
@Fork(value=3)
public class LoopUnroll {
    @Param({"16", "32", /* "64", "128", "256", "512", */ "1024"})
    private int VECLEN;

    private byte[][] a;
    private byte[][] b;
    private byte[][] c;

    @Setup
    public void init() {
        a = new byte[VECLEN][VECLEN];
        b = new byte[VECLEN][VECLEN];
        c = new byte[VECLEN][VECLEN];
    }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    private int run_workload1(int count, byte[][] a , byte[][] b, byte[][] c) {
        for(int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                a[i][j] = (byte)(b[i][j] + c[i][j]);
            }
        }
        return a[count][count];
    }

    @Benchmark
    public void workload1_caller(Blackhole bh) {
        int r = 0;
        for(int i = 0 ; i < 100; i++) {
            r += run_workload1(i % a.length, a, b, c);
        }
        bh.consume(r);
    }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    private int run_workload2(int count, byte[][] a , byte[][] b) {
        for(int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                a[i][j] = b[i][j];
            }
        }
        return a[count][count];
    }

    @Benchmark
    public void workload2_caller(Blackhole bh) {
        int r = 0;
        for(int i = 0 ; i < 100; i++) {
            r += run_workload2(i % a.length, a, b);
        }
        bh.consume(r);
    }
}
