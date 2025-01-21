/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8335664 8338924
 * @summary Ensure a program that ends with a JSR does not crash
 * @library /test/lib
 * @compile LastJsr.jasm
 * @compile LastJsrReachable.jasm
 * @run main/othervm -Xbatch LastJsrTest
 */

public class LastJsrTest {
    public static void main(String[] args) {
        for (int i = 0; i < 1000; ++i) {
            LastJsr.test();
            LastJsrReachable.test();
        }
        System.out.println("PASSED");
    }
}
