/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8319372
 * @summary CastII because of condition guarding it becomes top
 * @requires vm.compiler2.enabled
 * @run main/othervm -XX:CompileCommand=quiet -XX:CompileCommand=compileonly,TestTopCastIIOnUndetectedDeadPath2::test -XX:-TieredCompilation
 *                   -Xbatch -XX:+UnlockDiagnosticVMOptions -XX:+StressIGVN -XX:StressSeed=256120824 TestTopCastIIOnUndetectedDeadPath2
 * @run main/othervm -XX:CompileCommand=quiet -XX:CompileCommand=compileonly,TestTopCastIIOnUndetectedDeadPath2::test -XX:-TieredCompilation
 *                   -Xbatch -XX:+UnlockDiagnosticVMOptions -XX:+StressIGVN TestTopCastIIOnUndetectedDeadPath2
 */

public class TestTopCastIIOnUndetectedDeadPath2 {
    static int array[] = new int[100];

    static int test() {
        int res = 0;
        for (int i = 1; i < 100; ++i) {
            try {
                res = array[i - 1];
                int x = (42 % i);
            } catch (ArithmeticException e) {
            }
        }
        return res;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10_000; i++) {
            test();
        }
    }
}
