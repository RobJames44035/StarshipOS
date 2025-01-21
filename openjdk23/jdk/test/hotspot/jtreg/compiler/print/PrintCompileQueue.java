/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test
 * @bug 8230943
 * @summary possible deadlock was detected when ran with -XX:+CIPrintCompileQueue
 * @run main/othervm -Xcomp -XX:+UnlockDiagnosticVMOptions -XX:+CIPrintCompileQueue
 *                   compiler.print.PrintCompileQueue
 *
 */

package compiler.print;

public class PrintCompileQueue {
    public static void main(String[] args) {
        System.out.println("Passed");
    }
}
