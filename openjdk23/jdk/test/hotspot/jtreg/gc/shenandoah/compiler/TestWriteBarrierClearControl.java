/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * @test
 * @key stress randomness
 * @summary Clearing control during final graph reshape causes memory barrier to loose dependency on null check
 * @requires vm.gc.Shenandoah
 * @requires vm.flavor == "server"
 * @run main/othervm -XX:-BackgroundCompilation -XX:-UseOnStackReplacement -XX:-TieredCompilation
 *                   -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC
 *                   -XX:+UnlockDiagnosticVMOptions -XX:+StressLCM -XX:+StressGCM
 *                   TestWriteBarrierClearControl
 *
 */
public class TestWriteBarrierClearControl {

    int f;

    static void test1(TestWriteBarrierClearControl o) {
        o.f = 0x42;
    }

    static TestWriteBarrierClearControl fo = new TestWriteBarrierClearControl();

    static void test2() {
        TestWriteBarrierClearControl o = fo;
        o.f = 0x42;
    }

    static public void main(String[] args) {
        TestWriteBarrierClearControl o = new TestWriteBarrierClearControl();
        for (int i = 0; i < 20000; i++) {
            test1(o);
            test2();
        }
        try {
            test1(null);
        } catch (NullPointerException npe) {}
        fo = null;
        try {
            test2();
        } catch (NullPointerException npe) {}
    }
}
