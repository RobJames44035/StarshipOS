/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.share.gc.gp.string;

import nsk.share.test.*;
import nsk.share.gc.Memory;
import nsk.share.gc.gp.GarbageProducer;

/**
 * Garbage producer that creates random strings.
 */
public class RandomStringProducer implements GarbageProducer<String> {

    private int stringLengthLowerBound = 10;

    public RandomStringProducer() {
    }

    public RandomStringProducer(int stringLengthLowerBound) {
        this.stringLengthLowerBound = stringLengthLowerBound;
    }

    public String create(long memory) {
        int stringLengthUpperBound = (int) Math.min(memory / 2 - Memory.getObjectExtraSize(), Integer.MAX_VALUE);
        if (stringLengthUpperBound < stringLengthLowerBound) {
                stringLengthLowerBound = stringLengthUpperBound;
        }
        int length = stringLengthLowerBound + LocalRandom.nextInt(stringLengthUpperBound - stringLengthLowerBound);
        char[] arr = new char[length];
        for (int i = 0; i < length; ++i) {
            arr[i] = (char) LocalRandom.nextInt();
        }
        return new String(arr);
    }

    public void setStringLengthLowerBound(int stringLengthLowerBound) {
        this.stringLengthLowerBound = stringLengthLowerBound;
    }

    public void validate(String s) {
    }
}
