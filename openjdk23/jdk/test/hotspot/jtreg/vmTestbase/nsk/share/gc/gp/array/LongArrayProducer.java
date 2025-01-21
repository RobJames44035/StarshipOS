/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package nsk.share.gc.gp.array;

import nsk.share.test.LocalRandom;
import nsk.share.gc.gp.GarbageProducer;
import nsk.share.gc.Memory;
import nsk.share.TestFailure;

/**
 * GarbageProducer implementation that produces long arrays.
 */
public class LongArrayProducer implements GarbageProducer<long[]> {
        public long[] create(long memory) {
                long[] arr = new long[Memory.getArrayLength(memory, Memory.getLongSize())];
                LocalRandom.nextLongs(arr);
                return arr;
        }

        public void validate(long[] arr) {
                LocalRandom.validate(arr);
        }
}
