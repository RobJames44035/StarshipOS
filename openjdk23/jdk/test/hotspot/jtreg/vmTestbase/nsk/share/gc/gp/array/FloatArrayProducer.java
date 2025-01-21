/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package nsk.share.gc.gp.array;

import nsk.share.test.LocalRandom;
import nsk.share.gc.gp.GarbageProducer;
import nsk.share.gc.Memory;

/**
 * GarbageProducer implementation that produces float arrays.
 */
public class FloatArrayProducer implements GarbageProducer<float[]> {
        public float[] create(long memory) {
                float[] arr = new float[Memory.getArrayLength(memory, Memory.getFloatSize())];
                LocalRandom.nextFloats(arr);
                return arr;
        }

        public void validate(float[] arr) {
                LocalRandom.validate(arr);
        }
}
