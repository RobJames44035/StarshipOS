/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8086053
 *
 * @run main/othervm -Xcomp -XX:+UseTLAB -XX:+ZeroTLAB compiler.memoryinitialization.ZeroTLABTest
 * @run main/othervm -Xcomp -XX:+UseTLAB -XX:-ZeroTLAB compiler.memoryinitialization.ZeroTLABTest
 * @run main/othervm -Xcomp -XX:-UseTLAB -XX:+ZeroTLAB compiler.memoryinitialization.ZeroTLABTest
 * @run main/othervm -Xcomp -XX:-UseTLAB -XX:-ZeroTLAB compiler.memoryinitialization.ZeroTLABTest
 */

package compiler.memoryinitialization;

public class ZeroTLABTest {
    public static void main(String args[]) {
        System.out.println("Test PASSED");
    }
}
