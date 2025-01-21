/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package nsk.share.gc.gp.array;

import nsk.share.test.LocalRandom;
import nsk.share.gc.gp.GarbageProducer;
import nsk.share.gc.Memory;
import nsk.share.TestFailure;

/**
 * GarbageProducer implementation that produces byte arrays.
 */
public class BooleanArrayProducer implements GarbageProducer<boolean[]> {
        public boolean[] create(long memory) {
                boolean[] arr = new boolean[Memory.getArrayLength(memory, Memory.getBooleanSize())];
                LocalRandom.nextBooleans(arr);
                return arr;
        }

        public void validate(boolean[] arr) {
                LocalRandom.validate(arr);
        }
}
