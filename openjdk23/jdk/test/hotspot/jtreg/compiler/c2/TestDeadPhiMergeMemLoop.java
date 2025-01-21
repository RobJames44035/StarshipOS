/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8249605
 * @summary A dead memory loop is detected when replacing an actually dead memory phi node in PhiNode::Ideal()
 *          by a dead MergeMemNode which builds a cycle with one of its slices.
 *
 * @run main/othervm -Xcomp -XX:-TieredCompilation
 *                   -XX:CompileCommand=compileonly,compiler.c2.TestDeadPhiMergeMemLoop::main
 *                   -XX:CompileCommand=dontinline,compiler.c2.TestDeadPhiMergeMemLoop::dontInline
 *                   compiler.c2.TestDeadPhiMergeMemLoop
 */

package compiler.c2;

public class TestDeadPhiMergeMemLoop {

    public static boolean bFld = false;
    public static double dArrFld[] = new double[400];

    public static void main(String[] strArr) {
        int x = 1;
        int i = 0;
        int iArr[] = new int[400];
        dontInline();

        if (bFld) {
            x += x;
        } else if (bFld) {
            float f = 1;
            while (++f < 132) {
                if (bFld) {
                    dArrFld[5] = 3;
                    for (i = (int)(f); i < 12; i++) {
                    }
                }
            }
        }
    }

    // Not inlined
    public static void dontInline() {
    }
}
