/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8297642
 * @compile TestOnlyInfiniteLoops.jasm
 * @summary Nested irreducible loops, where the inner loop floats out of the outer
 * @run main/othervm
 *      -XX:CompileCommand=compileonly,TestOnlyInfiniteLoops::test*
 *      -XX:-TieredCompilation -Xcomp
 *      TestOnlyInfiniteLoopsMain
 *
 * @test
 * @bug 8297642
 * @compile TestOnlyInfiniteLoops.jasm
 * @summary Nested irreducible loops, where the inner loop floats out of the outer
 * @run main/othervm
 *      -XX:CompileCommand=compileonly,TestOnlyInfiniteLoops::test*
 *      -XX:-TieredCompilation -Xcomp
 *      -XX:PerMethodTrapLimit=0
 *      TestOnlyInfiniteLoopsMain
*/

public class TestOnlyInfiniteLoopsMain {
    public static void main(String[] args) {
        TestOnlyInfiniteLoops t = new TestOnlyInfiniteLoops();
        System.out.println("test_simple");
        t.test_simple(0, 0, 0);
        System.out.println("test_irreducible");
        t.test_irreducible(0, 0, 0, 0);
    }
}
