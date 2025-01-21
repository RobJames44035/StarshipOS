/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8324969
 * @summary C2 incorrectly marks unbalanced (after coarsened locks were eliminated)
 *          nested locks for elimination.
 * @requires vm.compMode != "Xint"
 * @run main/othervm -XX:-BackgroundCompilation TestCoarsenedAndNestedLocksElimination
 */

public class TestCoarsenedAndNestedLocksElimination {

    public static void main(String[] strArr) {
        for (int i = 0; i < 12000; ++i) {
            test1(-1);
            test2(-1);
        }
    }

    static synchronized int methodA(int var) {
        return var;
    }

    static synchronized int methodB(int var) {
        return var;
    }

    static int varA = 0;
    static int varB = 0;

    static void test1(int var) {
        synchronized (TestNestedLocksElimination.class) {
            for (int i2 = 0; i2 < 3; i2++) { // Fully unrolled
                 varA = methodA(i2);         // Nested synchronized methods also use
                 varB = i2 + methodB(var);   // TestNestedLocksElimination.class for lock
            }
        }
        TestNestedLocksElimination t = new TestNestedLocksElimination(); // Triggers EA
    }

    static boolean test2(int var) {
        synchronized (TestNestedLocksElimination.class) {
            for (int i1 = 0; i1 < 100; i1++) {
                switch (42) {
                case 42:
                    short[] sArr = new short[256]; // Big enough to avoid scalarization checks
                case 50:
                    for (int i2 = 2; i2 < 8; i2 += 2) { // Fully unrolled
                        for (int i3 = 1;;) {
                            int var1 = methodA(i2);
                            int var2 = i2 + methodB(i3);
                            break;
                        }
                    }
                }
            }
        }
        return var > 0;
    }
}
