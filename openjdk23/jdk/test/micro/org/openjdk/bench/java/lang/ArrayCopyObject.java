/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package org.openjdk.bench.java.lang;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.results.Result;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;




import java.util.concurrent.TimeUnit;
import java.util.Arrays;

class MyClass {
 public int field1;
 public int field2;
 public int field3;

 public MyClass(int val) {
   field1 = val;
   field2 = val;
   field3 = val;
 }
}

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 10, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(value = 3)
public class ArrayCopyObject {
    @Param({"31", "63", "127" , "2047" , "4095", "8191"}) private int size;

    private MyClass [] src;
    private MyClass [] dst;

    @Setup
    public void setup() {
      src = new MyClass[size];
      dst = new MyClass[size];
      for (int i = 0; i < src.length ; i++) {
        src[i] = new MyClass(i);
        dst[i] = new MyClass(0);
      }
    }

    @Benchmark
    public void disjoint_micro() {
      System.arraycopy(src, 0 , dst, 0 , size);
    }

    @Benchmark
    public void conjoint_micro() {
      System.arraycopy(src, 0 , src, 10 , size - 10 );
    }

    public static void main(String[] args) throws RunnerException {
       String [] base_opts =
          { "-XX:+UnlockDiagnosticVMOptions ",
            "-XX:+IgnoreUnrecognizedVMOptions ",
          "-XX:UseAVX=3" };
       String [] opts_str1 = {"-XX:-UseCompressedOops "};
       String [] opts_str2 = {"-XX:+UseCompressedOops "};

       Options baseOpts = new OptionsBuilder()
          .include(ArrayCopyObject.class.getName())
          .warmupTime(TimeValue.seconds(30))
          .measurementTime(TimeValue.seconds(10))
          .warmupIterations(1)
          .measurementIterations(2)
          .jvmArgs(base_opts)
          .forks(1)
          .build();

       RunResult r1 = new Runner(new OptionsBuilder()
         .parent(baseOpts)
         .jvmArgs(opts_str1)
         .build()).runSingle();

       RunResult r2 = new Runner(new OptionsBuilder()
         .parent(baseOpts)
         .jvmArgs(opts_str2)
         .build()).runSingle();

        System.out.println(r1.getPrimaryResult().getScore() + r2.getPrimaryResult().getScore());
    }
}

