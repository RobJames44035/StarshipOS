/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8067144
 * @summary -XX:+TraceDeoptimization tries to print realloc'ed objects even when there are none
 *
 * @run main/othervm -XX:-BackgroundCompilation -XX:-UseOnStackReplacement
 *                   -XX:+UnlockDiagnosticVMOptions -XX:+TraceDeoptimization
 *                   compiler.uncommontrap.TraceDeoptimizationNoRealloc
 */

package compiler.uncommontrap;

public class TraceDeoptimizationNoRealloc {

    static void m(boolean some_condition) {
        if (some_condition) {
            return;
        }
    }


    static public void main(String[] args) {
        for (int i = 0; i < 20000; i++) {
            m(false);
        }
        m(true);
    }
}
