/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8244719
 * @summary Tests custom bytecode with an explicit entry (fall through or jump) to an exception block which was unexpected for C2 parsing.
 *
 * @compile TestExceptionBlockWithPredecessors.jasm
 * @run main/othervm -Xbatch -XX:CompileCommand=dontinline,compiler.parsing.TestExceptionBlockWithPredecessors::*
 *                   compiler.parsing.TestExceptionBlockWithPredecessorsMain
 */

package compiler.parsing;

public class TestExceptionBlockWithPredecessorsMain {
    public static void main(String[] args) {
        TestExceptionBlockWithPredecessors t = new TestExceptionBlockWithPredecessors();
        for (int i = 0; i < 10000; i++) {
            t.loopCounter = (i % 2 == 0) ? 0 : 10;
            t.switchOn = (i % 2 == 0) ? 2 : 3;
            t.test(); // Triggers assertion failure
            t.testWorksWithoutPreds(); // Works since no predecessors for exception handler
        }
    }
}
