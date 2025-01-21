/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package compiler.conversions;

/*
 * @test
 * @bug 8253404
 * @requires vm.compiler2.enabled
 * @summary Tests that the optimization of a chain of integer additions followed
 *          by a long conversion does not lead to an explosion of live nodes.
 * @library /test/lib /
 * @run main/othervm -Xcomp -XX:-TieredCompilation
 *      -XX:CompileOnly=compiler.conversions.TestChainOfIntAddsToLongConversion::main
 *      -XX:MaxNodeLimit=1000 -XX:NodeLimitFudgeFactor=25
 *      compiler.conversions.TestChainOfIntAddsToLongConversion
 */

public class TestChainOfIntAddsToLongConversion {
    public static void main(String[] args) {
        long out = 0;
        for (int i = 0; i < 2; i++) {
            int foo = i;
            for (int j = 0; j < 17; j++) {
                // Int addition to be turned into a chain by loop unrolling.
                foo = foo + foo;
            }
            // Int to long conversion.
            out = foo;
        }
        System.out.println(out);
    }
}
