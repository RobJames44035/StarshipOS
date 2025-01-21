/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/**
 * @test
 * @bug 8313345
 * @summary Test SuperWord with a CMove that does not have a matching bool pack.
 * @requires vm.compiler2.enabled
 * @run main/othervm -XX:-TieredCompilation -XX:CompileCommand=compileonly,*TestCMoveWithoutBoolPack*::fill -Xbatch
 *                   compiler.vectorization.TestCMoveWithoutBoolPack
 * @run main/othervm -XX:-TieredCompilation -XX:CompileCommand=compileonly,*TestCMoveWithoutBoolPack*::fill -Xbatch
 *                   -XX:+UseCMoveUnconditionally
 *                   compiler.vectorization.TestCMoveWithoutBoolPack
 */

package compiler.vectorization;

public class TestCMoveWithoutBoolPack {

    public static void main(String[] args) {
        A a = new A();
        B b = new B();
        double[] c = new double[1000];
        for (int i = 0; i < 1000; i++) {
            a.fill(c);
            b.fill(c);
        }
    }

    public static class A {
        void fill(double[] array) {
            for (int i = 0; i < array.length; ++i) {
                array[i] = this.transform(array[i]);
            }
        }

        public double transform(double value) {
            return value * value;
        }
    }

    public static class B extends A {
        public double transform(double value) {
            return value;
        }
    }
}
