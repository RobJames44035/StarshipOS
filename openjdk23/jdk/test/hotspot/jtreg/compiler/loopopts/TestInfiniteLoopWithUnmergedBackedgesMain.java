/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8296412
 * @compile TestInfiniteLoopWithUnmergedBackedges.jasm
 * @summary Infinite loops may not have the backedges merged, before we call IdealLoopTree::check_safepts
 * @requires vm.compiler2.enabled
 * @run main/othervm -Xcomp -XX:-TieredCompilation -XX:-LoopUnswitching
 *      -XX:CompileCommand=compileonly,TestInfiniteLoopWithUnmergedBackedges::test*
 *      TestInfiniteLoopWithUnmergedBackedgesMain
 */

public class TestInfiniteLoopWithUnmergedBackedgesMain {
    public static void main (String[] args) {
        TestInfiniteLoopWithUnmergedBackedges.test_001(1, 0, 0, 0, 0);
        TestInfiniteLoopWithUnmergedBackedges.test_002(1, 0, 0, 0, 0);
        TestInfiniteLoopWithUnmergedBackedges.test_003(1, 0, 0, 0, 0);
        TestInfiniteLoopWithUnmergedBackedges.test_004(1, 0, 0, 0, 0);
    }
}
