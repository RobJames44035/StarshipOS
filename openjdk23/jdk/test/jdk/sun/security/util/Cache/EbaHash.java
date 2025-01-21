/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8186654
 * @summary Transpositions of an array result in the same
 *          EqualByteArray.hashCode()
 * @modules java.base/sun.security.util
 */

import sun.security.util.Cache;

public class EbaHash {
    public static void main(String[] args) throws Throwable {
        int h1 = new Cache.EqualByteArray(new byte[] {1,2,3}).hashCode();
        int h2 = new Cache.EqualByteArray(new byte[] {2,3,1}).hashCode();
        int h3 = new Cache.EqualByteArray(new byte[] {3,1,2}).hashCode();
        if (h1 == h2 && h2 == h3) {
            throw new RuntimeException("Transpositions of an array" +
                " resulted in the same hashCode()");
        }
    }
}
