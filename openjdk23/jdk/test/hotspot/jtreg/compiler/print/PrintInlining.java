/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8022585 8277055
 * @summary VM crashes when ran with -XX:+PrintInlining
 * @run main/othervm -Xcomp -XX:+UnlockDiagnosticVMOptions -XX:+PrintInlining
 *                   compiler.print.PrintInlining
 * @run main/othervm -Xcomp -XX:-TieredCompilation -XX:+UnlockDiagnosticVMOptions -XX:+PrintInlining
 *                   compiler.print.PrintInlining
 * @run main/othervm -Xcomp -XX:-TieredCompilation -XX:+UnlockDiagnosticVMOptions -XX:+PrintIntrinsics
 *                   compiler.print.PrintInlining
 */

package compiler.print;

public class PrintInlining {
    public static void main(String[] args) {
        System.out.println("Passed");
    }
}
