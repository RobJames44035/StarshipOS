/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test
 * @bug 8234377
 * @summary new BitArray(0).toString() throws ArrayIndexOutOfBoundsException
 * @modules java.base/sun.security.util
 */

import sun.security.util.BitArray;

public class EmptyBitArray {
    public static void main(String[] args) {
        new BitArray(0).toString();
    }
}
