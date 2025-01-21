/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package nsk.share.gc.gp.array;

import nsk.share.test.LocalRandom;
import nsk.share.gc.gp.GarbageProducer;
import nsk.share.gc.Memory;

/**
 * GarbageProducer implementation that produces double arrays.
 */
public class DoubleArrayProducer implements GarbageProducer<double[]> {
        public double[] create(long memory) {
                double[] arr = new double[Memory.getArrayLength(memory, Memory.getDoubleSize())];
                LocalRandom.nextDoubles(arr);
                return arr;
        }

        public void validate(double[] arr) {
                LocalRandom.validate(arr);
        }
}
