/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8308749
 * @compile TestCountedLoopInsideInfiniteLoop.jasm
 * @summary Counted Loops inside infinite loops are only detected later,
 *          and may still be a Region and not a LoopNode as expected.
 * @run main/othervm -Xcomp -XX:-TieredCompilation -XX:PerMethodTrapLimit=0
 *      -XX:CompileCommand=compileonly,TestCountedLoopInsideInfiniteLoop::test
 *      TestCountedLoopInsideInfiniteLoopMain
 */

public class TestCountedLoopInsideInfiniteLoopMain {
    public static void main (String[] args) {
        TestCountedLoopInsideInfiniteLoop.test(0, 0, 0, 0);
    }
}
