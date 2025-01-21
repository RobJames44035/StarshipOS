/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test
 * @bug 8227384
 * @summary C2 compilation fails with "graph should be schedulable" when running with -XX:-EliminateLocks
 * @requires vm.compiler2.enabled & !vm.graal.enabled
 *
 * @run main/othervm -XX:-EliminateLocks TestEliminateLocksOffCrash
 */

public class TestEliminateLocksOffCrash {
    public static void main(String[] args) {
        for (int i = 0; i < 20_000; i++) {
            try {
                test();
            } catch (Exception e) {
            }
        }
    }

    private static void test() throws Exception {
        Object obj = new Object();
        synchronized (obj) {
            throw new Exception();
        }
    }
}
