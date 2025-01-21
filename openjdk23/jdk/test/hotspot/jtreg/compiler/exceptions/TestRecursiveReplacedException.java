/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8054224
 * @summary Recursive method compiled by C1 is unable to catch StackOverflowError
 *
 * @run main/othervm -Xcomp -XX:+TieredCompilation -XX:TieredStopAtLevel=2 -Xss512K
 *      -XX:CompileCommand=compileonly,compiler.exceptions.TestRecursiveReplacedException::run
 *      compiler.exceptions.TestRecursiveReplacedException
 */

package compiler.exceptions;

public class TestRecursiveReplacedException {

    public static void main(String args[]) {
        new TestRecursiveReplacedException().run();
    }

    public void run() {
        try {
            run();
        } catch (Throwable t) {
        }
    }
}
