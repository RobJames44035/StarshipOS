/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package org.openjdk.bench.vm.compiler;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.Warmup;
import java.util.concurrent.TimeUnit;
import java.nio.charset.StandardCharsets;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(value = 3)
public class StringConstructorBenchmark {
  private byte[] array;
  private String str;

  @Setup
  public void setup() {
    str = "Quizdeltagerne spiste jordb\u00e6r med fl\u00f8de, mens cirkusklovnen. \u042f";//Latin1 ending with Russian
    array = str.getBytes(StandardCharsets.UTF_8);
  }

  @Benchmark
  public String newString()  {
      return new String(array, 0, array.length, StandardCharsets.UTF_8);
  }

  @Benchmark
  public String translateEscapes()  {
      return str.translateEscapes();
  }
}
