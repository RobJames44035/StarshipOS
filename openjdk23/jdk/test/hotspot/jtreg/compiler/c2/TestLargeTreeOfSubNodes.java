/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8316396
 * @summary Endless loop in C2 compilation triggered by AddNode::IdealIL
 * @run main/othervm  -XX:CompileCommand=compileonly,*TestLargeTreeOfSubNodes*::test -XX:-TieredCompilation -Xcomp TestLargeTreeOfSubNodes
 */

public class TestLargeTreeOfSubNodes {
    public static long res = 0;

    public static void test() {
        int a = -1, b = 0;
        for (int i = 0; i < 100; ++i) {
            for (int j = 0; j < 10; ++j) {
                for (int k = 0; k < 1; ++k) {
                }
                b -= a;
                a += b;
            }
        }
        res = a;
    }

    public static void main(String[] args) {
        test();
    }
}
