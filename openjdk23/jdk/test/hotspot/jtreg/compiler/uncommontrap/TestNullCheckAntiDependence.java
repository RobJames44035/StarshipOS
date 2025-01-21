/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package compiler.uncommontrap;

/**
 * @test
 * @bug 8261730 8265132
 * @summary Test that no anti-dependence violation is reported between a store
 *          used as an implicit null check and a load placed in the null block.
 * @run main/othervm -XX:-BackgroundCompilation
 *      compiler.uncommontrap.TestNullCheckAntiDependence
 */

public class TestNullCheckAntiDependence {

    private static class MyInteger {
        int val;
    }

    private static MyInteger foo = new MyInteger();
    private static MyInteger bar = new MyInteger();
    private static MyInteger[] global = {new MyInteger()};

    static void test1() {
        for (int i = 0; i < 1; i++) {
            // This load is placed in the null block.
            foo.val = -bar.val;
            for (int k = 0; k < 10; k++) {
                // This store is hoisted and used as an implicit null check.
                foo.val = 0;
            }
        }
    }

    static void test2(MyInteger a, MyInteger b) {
        global[0].val = a.val + b.val * 31;
        global[0].val = 0;
        return;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10_000; i++) {
            test1();
        }

        for (int i = 0; i < 10_000; i++) {
            test2(new MyInteger(), new MyInteger());
        }

    }

}
