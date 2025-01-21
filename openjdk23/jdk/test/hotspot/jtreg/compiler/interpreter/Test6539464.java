/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/**
 * @test
 * @bug 6539464
 * @summary Math.log() produces inconsistent results between successive runs.
 *
 * @run main/othervm -Xcomp
 *      -XX:CompileCommand=compileonly,compiler.interpreter.Test6539464::main
 *      compiler.interpreter.Test6539464
 */

package compiler.interpreter;

public class Test6539464 {
    static double log_value = 17197;
    static double log_result = Math.log(log_value);

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 1000000; i++) {
            double log_result2 = Math.log(log_value);
            if (log_result2 != log_result) {
                throw new InternalError("Math.log produces inconsistent results: " + log_result2 + " != " + log_result);
            }
        }
    }
}
