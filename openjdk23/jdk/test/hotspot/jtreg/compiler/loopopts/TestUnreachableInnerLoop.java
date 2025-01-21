/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/**
 * @test
 * @bug 8289954
 * @summary C2: Assert failed in PhaseCFG::verify() after JDK-8183390
 *
 * @run main/othervm -Xcomp -Xbatch
 *      -XX:CompileOnly=compiler.loopopts.TestUnreachableInnerLoop::*
 *      compiler.loopopts.TestUnreachableInnerLoop
 */

package compiler.loopopts;

public class TestUnreachableInnerLoop {

    public static int field = 0;
    public static int arr[] = new int[500];

    public static void fun() {
        for (int elem : arr) {
            int x = 1, y = 2, z = 3;
            int i, j;

            // This is a good loop
            for (i = 2; i < 63; i++) {
                arr[i] = arr[i] + 3592870;
            }

            // The inner loop looks quite complex but it's unreachable
            // code as loop condition "k < 2" never satisfies
            for (j = 3; j < 63; j++) {
                for (int k = j; k < 2; k++) {
                    arr[j] <<= k;
                    try {
                        x = k / i;
                        y = j % 6;
                        arr[k] = 88 % elem;
                    } catch (ArithmeticException ex) {}
                    switch (2) {
                        case 2: {
                            try {
                                y = arr[j] % y;
                                z = x / 2345;
                                elem = j % -2;
                            } catch (ArithmeticException ex) {}
                            break;
                        }
                        case 3: {
                            y = arr[j] / 2;
                            z -= k;
                            break;
                        }
                    }
                    arr[100] -= j;
                    field += k;
                }
            }
        }
    }

    public static void main(String[] args) {
        fun();
    }
}
