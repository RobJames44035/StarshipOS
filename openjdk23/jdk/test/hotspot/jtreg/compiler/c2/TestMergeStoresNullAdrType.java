/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package compiler.c2;

/*
 * @test
 * @bug 8318446 8331085
 * @summary Test merge stores, when "adr_type() == nullptr" because of TOP somewhere in the address.
 * @run main/othervm -XX:CompileCommand=compileonly,compiler.c2.TestMergeStoresNullAdrType::test
 *                   -XX:-TieredCompilation -Xcomp
 *                   -XX:+UnlockDiagnosticVMOptions -XX:+StressIGVN -XX:+StressCCP
 *                   -XX:RepeatCompilation=1000
 *                   compiler.c2.TestMergeStoresNullAdrType
 * @run main compiler.c2.TestMergeStoresNullAdrType
 */

public class TestMergeStoresNullAdrType {
    static int arr[] = new int[100];

    static void test() {
        boolean b = false;
        for (int k = 269; k > 10; --k) {
            b = b;
            int j = 6;
            while ((j -= 3) > 0) {
                if (b) {
                } else {
                    arr[j] >>= 2;
                }
            }
        }
    }

    public static void main(String[] args) {
        test();
    }
}
