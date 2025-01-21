/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package compiler.vectorapi;

import java.lang.foreign.MemorySegment;
import jdk.incubator.vector.*;
import java.nio.ByteOrder;

/*
 * @test
 * @bug 8262998
 * @summary Vector API intrinsincs should not modify IR when bailing out
 * @modules jdk.incubator.vector
 * @run main/othervm -Xbatch -XX:+IgnoreUnrecognizedVMOptions -XX:UseAVX=1
 *                   -XX:-TieredCompilation compiler.vectorapi.TestIntrinsicBailOut
 */

/*
 * @test
 * @bug 8317299
 * @summary Vector API intrinsincs should handle JVM state correctly whith late inlining when compiling with -InlineUnsafeOps
 * @modules jdk.incubator.vector
 * @requires vm.cpu.features ~= ".*avx512.*"
 * @run main/othervm -Xcomp -XX:+UnlockDiagnosticVMOptions -XX:-InlineUnsafeOps -XX:+IgnoreUnrecognizedVMOptions -XX:UseAVX=3
 *                   -XX:CompileCommand=compileonly,compiler.vectorapi.TestIntrinsicBailOut::test -XX:CompileCommand=quiet
 *                   -XX:-TieredCompilation compiler.vectorapi.TestIntrinsicBailOut
 */


public class TestIntrinsicBailOut {
  static final VectorSpecies<Double> SPECIES256 = DoubleVector.SPECIES_256;
  static byte[] a = new byte[512];
  static byte[] r = new byte[512];
  static MemorySegment msa = MemorySegment.ofArray(a);
  static MemorySegment msr = MemorySegment.ofArray(r);

  static void test() {
    DoubleVector av = DoubleVector.fromMemorySegment(SPECIES256, msa, 0, ByteOrder.BIG_ENDIAN);
    av.intoMemorySegment(msr, 0, ByteOrder.BIG_ENDIAN);

    DoubleVector bv = DoubleVector.fromMemorySegment(SPECIES256, msa, 32, ByteOrder.LITTLE_ENDIAN);
    bv.intoMemorySegment(msr, 32, ByteOrder.LITTLE_ENDIAN);
  }

  public static void main(String[] args) {
    for (int i = 0; i < 15000; i++) {
      test();
    }
    System.out.println(r[0] + r[32]);
  }
}
