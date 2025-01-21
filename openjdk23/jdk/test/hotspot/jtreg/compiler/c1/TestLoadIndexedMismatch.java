/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * @test
 * @bug 8313402
 * @summary C1: Incorrect LoadIndexed value numbering
 * @requires vm.compiler1.enabled
 * @library /compiler/patches /test/lib
 * @build java.base/java.lang.Helper
 * @run main/othervm -Xbatch -XX:CompileThreshold=100
 *                   -XX:TieredStopAtLevel=1
 *                   compiler.c1.TestLoadIndexedMismatch
 */

package compiler.c1;

public class TestLoadIndexedMismatch {
    static final byte[] ARR = {42, 42};
    static final char EXPECTED_CHAR = (char)(42*256 + 42);

    public static char work() {
        // LoadIndexed (B)
        byte b = ARR[0];
        // StringUTF16.getChar intrinsic, LoadIndexed (C)
        char c = Helper.getChar(ARR, 0);
        return c;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10_000; i++) {
            char c = work();
            if (c != EXPECTED_CHAR) {
                throw new IllegalStateException("Read: " + (int)c + ", expected: " + (int)EXPECTED_CHAR);
            }
        }
    }
}
