/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */
package nsk.share.test;

import java.util.Arrays;

public class LazyIntArrayToString {

    private int[] array;

    public LazyIntArrayToString(int[] array) {
        this.array = array;
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }

}
