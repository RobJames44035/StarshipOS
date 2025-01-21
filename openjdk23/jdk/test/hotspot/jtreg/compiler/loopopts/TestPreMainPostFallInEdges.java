/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8249602
 * @summary Tests the rewiring and cloning of fall-in values from the main loop (and pre loop) to the post (and main loop)
 *          which resulted in a DUIterator_Fast assertion failure due to an insertion in the outputs of the loop header node.
 *
 * @run main compiler.loopopts.TestPreMainPostFallInEdges
 */

package compiler.loopopts;

public class TestPreMainPostFallInEdges {

    public static int test() {
        int iArr[] = new int[400];
        float fArr[] = new float[400];
        int x = 0;
        byte y = 124;
        short z = 0;

        int i = 1;
        do {
            int j = 1;
            do {
                z *= 11;

                // These 4 array stores live in the back control block and cannot float. They are cloned and their control input set to the preheader control
                // block of the post loop. If their input edges also have their placement in the back control block (get_ctrl == back control) then they are
                // cloned as well. The following code hits the assertion failure when we clone a node with a control edge to the loop header block of the main.
                // loop. The DUIterator_Fast does not allow insertions. The fix is to replace it by a normal DUIterator which allows insertions.
                iArr[j] = 3;
                // load of iArr[j + 1] is also cloned but has a control edge to the main loop header block hitting the assertion failure.
                iArr[j + 1] += 4;
                fArr[j] = 5;
                fArr[j + 1] += fArr[j + 5]; // same for load of fArr[j + 1] and load of fArr[j + 5]

                int k = 1;
                do {
                    iArr[j] *= 324;
                    x = 34;
                    y *= 54;
                } while (k < 1);
            } while (++j < 6);
        } while (++i < 289);
        return checkSum(iArr) + checkSum(fArr);
    }

    public static int checkSum(int[] a) {
        int sum = 0;
        for (int j = 0; j < a.length; j++) {
            sum += a[j] % (j + 1);
        }
        return sum;
    }

    public static int checkSum(float[] a) {
        int sum = 0;
        for (int j = 0; j < a.length; j++) {
            sum += a[j] % (j + 1);
        }
        return sum;
    }

    public static void main(String[] strArr) {
        for (int i = 0; i < 10000; i++) {
            test();
        }
    }
}

