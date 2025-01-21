/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * @test TestUseCompiler
 * @bug 8086068
 * @summary Tests execution with inconsistent UseCompiler flag combination.
 *
 * @run main/othervm -Xint -XX:+UseCompiler compiler.arguments.TestUseCompiler
 * @run main/othervm -XX:+UseCompiler -Xint compiler.arguments.TestUseCompiler
 */

package compiler.arguments;

public class TestUseCompiler {

    public static void main(String args[]) {
        System.out.println("Passed");
    }
}

