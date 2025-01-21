/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * @test
 * @summary Tests correct code generation of lightweight locking when using -XX:+ShowMessageBoxOnError; times-out on failure
 * @bug 8329726
 * @run main/othervm -XX:+ShowMessageBoxOnError -Xcomp -XX:-TieredCompilation -XX:CompileOnly=TestLWLockingCodeGen::sync TestLWLockingCodeGen
 */
public class TestLWLockingCodeGen {
    private static int val = 0;
    public static void main(String[] args) {
        sync();
    }
    private static synchronized void sync() {
        val = val + (int)(Math.random() * 42);
    }
}
