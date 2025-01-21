/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @key randomness
 * @bug 8026844
 * @summary Test multiplyExact
 * @library /test/lib /
 * @modules java.base/jdk.internal.misc
 *          java.management
 *
 * @run main compiler.intrinsics.mathexact.MulExactILoadTest
 */

package compiler.intrinsics.mathexact;

public class MulExactILoadTest {
    public static void main(String[] args) {
        Verify.LoadTest.init();
        Verify.LoadTest.verify(new Verify.MulExactI());
    }
}
