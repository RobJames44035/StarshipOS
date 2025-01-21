/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package compiler.predicates;

import compiler.lib.ir_framework.*;
import jdk.test.lib.Asserts;

/*
 * @test
 * @bug 8325451
 * @summary Test basic loop predicates
 * @library /test/lib /
 * @run driver compiler.predicates.TestPredicatesBasic
 */
public class TestPredicatesBasic {
    public static final int size = 100;

    public static void main(String[] args) {
        TestFramework.run();
    }

    @DontInline
    private void blackhole(int i) {}

    @DontInline
    private int[] getArr() {
        int[] arr = new int[size];
        for (int i = 0; i < size; ++i) {
            arr[i] = i;
        }
        return arr;
    }

    @Test
    // Null check, loop entrance check, array upper bound check
    @IR(counts = {IRNode.IF, "3"})
    public void basic() {
        int[] arr = getArr();
        for (int i = 0; i < arr.length; ++i) {
            blackhole(arr[i]);
        }
    }

    @Test
    // Null check, loop entrance check, array upper bound check
    @IR(counts = {IRNode.IF, "4"})
    public void basicMinus() {
        int[] arr = getArr();
        for (int i = 0; i < arr.length - 1; ++i) {
            blackhole(arr[i]);
        }
    }

    @Test
    // Null check, loop entrance check, array lower/upper bound check
    @IR(counts = {IRNode.IF, "4"})
    public void basicNeg() {
        int[] arr = getArr();
        for (int i = arr.length - 1; i >= 0; --i) {
            blackhole(arr[i]);
        }
    }

    @Test
    @Arguments(values = {Argument.NUMBER_42})
    // Null check, loop entrance check, array lower/upper bound check
    @IR(counts = {IRNode.IF, "4"})
    public void basicLimit(int limit) {
        int[] arr = getArr();
        for (int i = 0; i < limit; ++i) {
            blackhole(arr[i]);
        }
    }
}

