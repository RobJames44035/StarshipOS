/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8316746
 * @summary During OSR, locks get transferred from interpreter frame.
 *          Check that unlocking 2 such locks works in the OSR compiled nmethod.
 *          Some platforms verify that the unlocking happens in the corrent order.
 *
 * @run main/othervm -Xbatch TestUnlockOSR
 */

public class TestUnlockOSR {
    static void test_method(Object a, Object b, int limit) {
        synchronized(a) { // allocate space for monitors
            synchronized(b) {
            }
        } // free space to test allocation in reused space
        synchronized(a) { // reuse the space
            synchronized(b) {
                for (int i = 0; i < limit; i++) {}
            }
        }
    }

    public static void main(String[] args) {
        Object a = new TestUnlockOSR(),
               b = new TestUnlockOSR();
        // avoid uncommon trap before last unlocks
        for (int i = 0; i < 100; i++) { test_method(a, b, 0); }
        // trigger OSR
        test_method(a, b, 100000);
    }
}
