/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8215044
 * @summary C2 crash in loopTransform.cpp with assert(cl->trip_count() > 0) failed: peeling a fully unrolled loop
 *
 * @run main/othervm -XX:CompileOnly=PeelingZeroTripCount::test PeelingZeroTripCount
 *
 */

public class PeelingZeroTripCount {

    public static void main(String[] args) {
        PeelingZeroTripCount issue = new PeelingZeroTripCount();
        for (int i = 0; i < 10000; i++) {
            issue.test(new int[999]);
        }
    }

    public void test(int[] iaarg) {
        int[] iarr = new int[777];
        for (int i = 4; i > 0; i--) {
            for (int j = 0; j <= i - 1; j++) {
                int istep = 2 * j - i + 1;
                int iadj = 0;
                if (istep < 0) {
                    iadj = iarr[0-istep] + iaarg[i-1];
                } else {
                    iadj = iarr[istep] + iaarg[i-1];
                }
            }
        }
    }
}
