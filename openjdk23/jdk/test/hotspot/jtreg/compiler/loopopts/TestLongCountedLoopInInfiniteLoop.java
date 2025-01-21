/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8336478
 * @summary C2: assert(!n->as_Loop()->is_loop_nest_inner_loop() || _loop_opts_cnt == 0) failed: should have been turned into a counted loop
 * @compile LongCountedLoopInInfiniteLoop.jasm
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -Xcomp -XX:PerMethodTrapLimit=0 -XX:PerMethodSpecTrapLimit=0
 *                   -XX:+IgnoreUnrecognizedVMOptions -XX:StressLongCountedLoop=2000000
 *                   -XX:CompileCommand=compileonly,TestLongCountedLoopInInfiniteLoop::test TestLongCountedLoopInInfiniteLoop
 */

public class TestLongCountedLoopInInfiniteLoop {
    public static void main(String[] args) {
        LongCountedLoopInInfiniteLoop obj = new LongCountedLoopInInfiniteLoop();
        test(false, obj);
    }

    private static void test(boolean flag, LongCountedLoopInInfiniteLoop obj) {
        if (flag) {
            obj.test();
        }
    }
}
