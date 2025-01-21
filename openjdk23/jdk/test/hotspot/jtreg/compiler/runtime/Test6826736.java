/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/**
 * @test
 * @bug 6826736
 * @summary CMS: core dump with -XX:+UseCompressedOops
 *
 * @requires vm.bits == 64
 * @run main/othervm/timeout=600 -XX:+IgnoreUnrecognizedVMOptions -Xbatch
 *      -XX:+ScavengeALot -XX:+UseCompressedOops -XX:HeapBaseMinAddress=32g
 *      -XX:CompileThreshold=100 -XX:-BlockLayoutRotateLoops
 *      -XX:LoopUnrollLimit=0 -Xmx256m -XX:ParallelGCThreads=4
 *      -XX:CompileCommand=compileonly,compiler.runtime.Test6826736::test
 *      compiler.runtime.Test6826736
 */

package compiler.runtime;

public class Test6826736 {
    int[] arr;
    int[] arr2;
    int test(int r) {
        for (int i = 0; i < 100; i++) {
            for (int j = i; j < 100; j++) {
               int a = 0;
               for (long k = 0; k < 100; k++) {
                  a += k;
               }
               if (arr != null)
                   a = arr[j];
               r += a;
            }
        }
        return r;
    }

    public static void main(String[] args) {
        int r = 0;
        Test6826736 t = new Test6826736();
        for (int i = 0; i < 100; i++) {
            t.arr = new int[100];
            r = t.test(r);
        }
        System.out.println("Warmup 1 is done.");
        for (int i = 0; i < 100; i++) {
            t.arr = null;
            r = t.test(r);
        }
        System.out.println("Warmup 2 is done.");
        for (int i = 0; i < 100; i++) {
            t.arr = new int[100];
            r = t.test(r);
        }
        System.out.println("Warmup is done.");
        for (int i = 0; i < 100; i++) {
            t.arr = new int[1000000];
            t.arr = null;
            r = t.test(r);
        }
    }
}
