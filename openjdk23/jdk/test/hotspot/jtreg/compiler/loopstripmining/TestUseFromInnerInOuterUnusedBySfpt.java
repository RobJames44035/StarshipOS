/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/**
 * @test
 * @bug 8295788
 * @summary C2 compilation hits "assert((mode == ControlAroundStripMined && use == sfpt) || !use->is_reachable_from_root()) failed: missed a node"
 *
 * @run main/othervm -Xcomp -XX:CompileOnly=TestUseFromInnerInOuterUnusedBySfpt::* TestUseFromInnerInOuterUnusedBySfpt
 *
 */

public class TestUseFromInnerInOuterUnusedBySfpt {

    public static final int N = 400;

    public static void dMeth(long l, int i5, int i6) {

        int i7=14, i8=-14, i9=7476, i11=0;
        long lArr[]=new long[N];

        for (i7 = 3; i7 < 177; i7++) {
            lArr[i7 + 1] >>= l;
            l -= i8;
            i6 = (int)l;
        }
        for (i9 = 15; i9 < 356; i9 += 3) {
            i11 = 14;
            do {
                i5 |= i6;
            } while (--i11 > 0);
        }
    }

    public static void main(String[] strArr) {
        TestUseFromInnerInOuterUnusedBySfpt _instance = new TestUseFromInnerInOuterUnusedBySfpt();
        for (int i = 0; i < 10; i++) {
            _instance.dMeth(-12L, -50242, 20);
        }
    }
}
