/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/**
 * @test
 * @bug 8268672
 * @summary C2: assert(!loop->is_member(u_loop)) failed: can be in outer loop or out of both loops only
 *
 * @run main/othervm -Xcomp -XX:-TieredCompilation -XX:CompileOnly=TestPinnedNodeInInnerLoop::* TestPinnedNodeInInnerLoop
 *
 */

public class TestPinnedNodeInInnerLoop {
    boolean b;
    double d;
    int iArr[];

    public static void main(String[] args) {
        TestPinnedNodeInInnerLoop t = new TestPinnedNodeInInnerLoop();
        for (int i = 0; i < 10; i++) {
            t.test();
        }
    }

    void test() {
        int e = 4, f = -51874, g = 7, h = 0;

        for (; f < 3; ++f) {
        }
        while (++g < 2) {
            if (b) {
                d = h;
            } else {
                iArr[g] = e;
            }
        }
        System.out.println(g);
    }
}
