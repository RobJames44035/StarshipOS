/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8289127
 * @summary Apache Lucene triggers: DEBUG MESSAGE: duplicated predicate failed which is impossible
 * @run main/othervm -XX:-BackgroundCompilation -XX:-UseOnStackReplacement -XX:-TieredCompilation TestMissedOpaqueInPredicate
 */

public class TestMissedOpaqueInPredicate {
    public static void main(String[] args) {
        long[] tmp = new long[28];
        long[] longs = new long[32];
        for (int i = 0; i < 20_000; i++) {
            test(tmp, longs);
        }
    }

    private static void test(long[] tmp, long[] longs) {
        for (int iter = 0, tmpIdx = 0, longsIdx = 28; iter < 4; ++iter, tmpIdx += 7, longsIdx += 1) {
            long l0 = tmp[tmpIdx + 0] << 12;
            l0 |= tmp[tmpIdx + 1] << 10;
            l0 |= tmp[tmpIdx + 2] << 8;
            l0 |= tmp[tmpIdx + 3] << 6;
            l0 |= tmp[tmpIdx + 4] << 4;
            l0 |= tmp[tmpIdx + 5] << 2;
            l0 |= tmp[tmpIdx + 6] << 0;
            longs[longsIdx + 0] = l0;
        }
    }
}
