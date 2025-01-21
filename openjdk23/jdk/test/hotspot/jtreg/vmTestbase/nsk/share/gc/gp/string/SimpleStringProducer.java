/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package nsk.share.gc.gp.string;

import nsk.share.test.*;
import nsk.share.gc.gp.GarbageProducer;

/**
 * Garbage producer that creates simple strings.
 */
public class SimpleStringProducer implements GarbageProducer<String> {


    public String create(long memory) {
        long length = memory/8;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; ++i)
            sb.append((char) LocalRandom.nextInt());
        return sb.toString();
    }


    public void validate(String s) {
    }
}
