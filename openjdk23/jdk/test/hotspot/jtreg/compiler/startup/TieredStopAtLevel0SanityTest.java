/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8154151
 * @summary Sanity test flag combo that force compiles on level 0
 *
 * @run main/othervm -Xcomp -XX:+UnlockDiagnosticVMOptions -XX:TieredStopAtLevel=0
 *                   compiler.startup.TieredStopAtLevel0SanityTest
 */

package compiler.startup;

public class TieredStopAtLevel0SanityTest {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello world!");
    }
}
