/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package nsk.share.gc.gp.array;

import nsk.share.test.LocalRandom;
import nsk.share.gc.gp.GarbageProducer;
import nsk.share.gc.Memory;
import nsk.share.TestFailure;

/**
 * GarbageProducer implementation that produces short arrays.
 */
public class ShortArrayProducer implements GarbageProducer<short[]> {
        public short[] create(long memory) {
                short[] arr = new short[Memory.getArrayLength(memory, Memory.getShortSize())];
                LocalRandom.nextShorts(arr);
                return arr;
        }

        public void validate(short[] arr) {
                LocalRandom.validate(arr);
        }
}
