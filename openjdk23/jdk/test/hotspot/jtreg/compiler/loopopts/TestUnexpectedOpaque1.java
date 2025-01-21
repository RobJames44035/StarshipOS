/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @key stress randomness
 * @bug 8298520
 * @requires vm.compiler2.enabled
 * @summary Dying subgraph confuses logic that tries to find the OpaqueZeroTripGuard.
 * @run main/othervm -Xcomp -XX:CompileCommand=compileonly,compiler.loopopts.TestUnexpectedOpaque1::* -XX:-TieredCompilation
 *                   -XX:+UnlockDiagnosticVMOptions -XX:+StressIGVN -XX:StressSeed=1642564308 compiler.loopopts.TestUnexpectedOpaque1
 * @run main/othervm -Xcomp -XX:CompileCommand=compileonly,compiler.loopopts.TestUnexpectedOpaque1::* -XX:-TieredCompilation
 *                   -XX:+UnlockDiagnosticVMOptions -XX:+StressIGVN compiler.loopopts.TestUnexpectedOpaque1
 */

package compiler.loopopts;

public class TestUnexpectedOpaque1 {

    static int cnt = 0;

    static int test() {
        int res = 42;
        for (int i = 500; i > 0; --i) {
            res |= 1;
            if (res != 0) {
                return 43;
            }
            cnt++;
        }
        return Float.floatToIntBits(44F);
    }

    public static void main(String[] args) {
        test();
    }
}
