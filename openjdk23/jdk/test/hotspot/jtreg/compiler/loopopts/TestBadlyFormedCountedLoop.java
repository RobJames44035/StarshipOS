/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8273115
 * @summary CountedLoopEndNode::stride_con crash in debug build with -XX:+TraceLoopOpts
 * @run main/othervm -XX:+IgnoreUnrecognizedVMOptions -XX:+TraceLoopOpts -Xcomp -XX:-TieredCompilation
 *                   -XX:CompileOnly=TestBadlyFormedCountedLoop::main TestBadlyFormedCountedLoop
 */

public class TestBadlyFormedCountedLoop {
    static int y;
    static int[] A = new int[1];

    public static void main(String[] args) {
        for (int i = 0; i < 10; i+=2) {
            int k;
            int j;
            for (j = 1; (j += 3) < 5; ) {
                A[0] = 0;
                for (k = j; k < 5; k++) {
                    y++;
                }
            }
            y = j;
        }
    }

}
