/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/**
 * @test
 * @summary Miscompilation in Unsafe off-heap swap routines
 * @requires vm.gc.Shenandoah
 * @modules java.base/jdk.internal.misc:+open
 *
 * @run main/othervm -XX:-UseOnStackReplacement -XX:-BackgroundCompilation -XX:-TieredCompilation
 *                   -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC
 *                   TestUnsafeOffheapSwap
 */

import java.util.*;
import jdk.internal.misc.Unsafe;

public class TestUnsafeOffheapSwap {

    static final int SIZE = 10000;
    static final long SEED = 1;

    static final jdk.internal.misc.Unsafe UNSAFE = Unsafe.getUnsafe();
    static final int SCALE = UNSAFE.ARRAY_INT_INDEX_SCALE;

    static Memory mem;
    static int[] arr;

    public static void main(String[] args) throws Exception {
        // Bug is exposed when memory.addr is not known statically
        mem = new Memory(SIZE*SCALE);
        arr = new int[SIZE];

        for (int i = 0; i < 10; i++) {
            test();
        }
    }

    static void test() {
        Random rnd = new Random(SEED);
        for (int i = 0; i < SIZE; i++) {
            int value = rnd.nextInt();
            mem.setInt(i, value);
            arr[i] = value;
        }

        for (int i = 0; i < SIZE; i++) {
            if (arr[i] != mem.getInt(i)) {
                throw new IllegalStateException("TESTBUG: Values mismatch before swaps");
            }
        }

        for (int i = 1; i < SIZE; i++) {
            mem.swap(i - 1, i);
            int tmp = arr[i - 1];
            arr[i - 1] = arr[i];
            arr[i] = tmp;
        }

        for (int i = 0; i < SIZE; i++) {
            if (arr[i] != mem.getInt(i)) {
                throw new IllegalStateException("Values mismatch after swaps");
            }
        }
    }

    static class Memory {
        private final long addr;

        Memory(int size) {
            addr = UNSAFE.allocateMemory(size);
        }

        public int getInt(int idx) {
            return UNSAFE.getInt(addr + idx*SCALE);
        }

        public void setInt(int idx, int val) {
            UNSAFE.putInt(addr + idx*SCALE, val);
        }

        public void swap(int a, int b) {
            int tmp = getInt(a);
            setInt(a, getInt(b));
            setInt(b, tmp);
        }
    }
}
