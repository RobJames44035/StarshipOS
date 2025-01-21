/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

 package org.openjdk.bench.vm.compiler;

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

 import java.util.concurrent.TimeUnit;

 @State(Scope.Thread)
 @BenchmarkMode(Mode.AverageTime)
 @OutputTimeUnit(TimeUnit.NANOSECONDS)
 @Warmup(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
 @Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
 @Fork(value = 3)
 public class ComputePI {

   @Benchmark
   public double compute_pi_int_dbl() {
     double pi = 4.0;
     boolean sign = false;

     for (int i = 3; i < 1000; i += 2) {
       if (sign) {
         pi += 4.0 / i;
       } else {
         pi -= 4.0 / i;
       }
       sign = !sign;
     }
     return pi;
   }

   @Benchmark
   public double compute_pi_int_flt() {
     float pi = 4.0f;
     boolean sign = false;

     for (int i = 3; i < 1000; i += 2) {
       if (sign) {
         pi += 4.0f / i;
       } else {
         pi -= 4.0f / i;
       }
       sign = !sign;
     }
     return pi;
   }

   @Benchmark
   public double compute_pi_long_dbl() {
     double pi = 4.0;
     boolean sign = false;

     for (long i = 3; i < 1000; i += 2) {
       if (sign) {
         pi += 4.0 / i;
       } else {
         pi -= 4.0 / i;
       }
       sign = !sign;
     }
     return pi;
   }

   @Benchmark
   public double compute_pi_long_flt() {
     float pi = 4.0f;
     boolean sign = false;

     for (long i = 3; i < 1000; i += 2) {
       if (sign) {
         pi += 4.0f / i;
       } else {
         pi -= 4.0f / i;
       }
       sign = !sign;
     }
     return pi;
   }

   @Benchmark
   public double compute_pi_flt_dbl() {
     double pi = 4.0;
     boolean sign = false;

     for (float i = 3.0f; i < 1000.0f; i += 2.0f) {
       if (sign) {
         pi += 4.0 / i;
       } else {
         pi -= 4.0 / i;
       }
       sign = !sign;
     }
     return pi;
   }

   @Benchmark
   public double compute_pi_dbl_flt() {
     float pi = 4.0f;
     boolean sign = false;

     for (float i = 3.0f; i < 1000.0f; i += 2.0f) {
       if (sign) {
         pi += 4.0f / i;
       } else {
         pi -= 4.0f / i;
       }
       sign = !sign;
     }
     return pi;
   }
 }
